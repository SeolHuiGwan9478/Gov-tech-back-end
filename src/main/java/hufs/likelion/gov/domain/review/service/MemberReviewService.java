package hufs.likelion.gov.domain.review.service;

import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.authentication.repository.MemberRepository;
import hufs.likelion.gov.domain.review.dto.GetMemberReviewsData;
import hufs.likelion.gov.domain.review.dto.GetMemberReviewsResponse;
import hufs.likelion.gov.domain.review.dto.PostMemberReviewRequest;
import hufs.likelion.gov.domain.review.dto.PostMemberReviewResponse;
import hufs.likelion.gov.domain.review.entity.MemberReview;
import hufs.likelion.gov.domain.review.entity.MemberReviewKeyword;
import hufs.likelion.gov.domain.review.repository.MemberReviewKeywordRepository;
import hufs.likelion.gov.domain.review.repository.MemberReviewRepository;
import hufs.likelion.gov.domain.review.repository.query.MemberReviewQueryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hufs.likelion.gov.global.constant.GlobalConstant.NOT_FOUND_MEMBER_ERR_MSG;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberReviewService {
    private final MemberReviewRepository memberReviewRepository;
    private final MemberReviewQueryRepository memberReviewQueryRepository;
    private final MemberReviewKeywordRepository memberReviewKeywordRepository;
    private final MemberRepository memberRepository;

    public GetMemberReviewsResponse getReviews(Authentication authentication){
        Member authMember = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        List<MemberReview> myReviews = memberReviewQueryRepository.findReviewsByOwner(authMember);
        List<GetMemberReviewsData> myReviewsData = myReviews.stream()
                .map(GetMemberReviewsData::toGetMemberReviewsData)
                .toList();
        return GetMemberReviewsResponse.builder()
                .totalCounts(myReviewsData.size())
                .data(myReviewsData)
                .build();
    }

    public PostMemberReviewResponse createReview(Authentication authentication, PostMemberReviewRequest dto){
        Member authMember = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        Member owner = memberRepository.findByMemberId(dto.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        MemberReview newMemberReview = MemberReview.builder()
                .owner(owner)
                .writer(authMember)
                .content(dto.getContent())
                .score(dto.getScore())
                .build();
        List<MemberReviewKeyword> newKeywords = dto.getKeywords().stream().map((keyword) -> MemberReviewKeyword.builder()
                .memberReview(newMemberReview)
                .keyword(keyword)
                .build()).toList();
        memberReviewRepository.save(newMemberReview);
        memberReviewKeywordRepository.saveAll(newKeywords);
        return PostMemberReviewResponse.builder().build();
    }
}