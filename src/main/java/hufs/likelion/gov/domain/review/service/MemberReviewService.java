package hufs.likelion.gov.domain.review.service;

import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.authentication.repository.MemberRepository;
import hufs.likelion.gov.domain.review.dto.GetMemberReviewsData;
import hufs.likelion.gov.domain.review.dto.GetMemberReviewsResponse;
import hufs.likelion.gov.domain.review.dto.PostMemberReviewRequest;
import hufs.likelion.gov.domain.review.dto.PostMemberReviewResponse;
import hufs.likelion.gov.domain.review.entity.MemberReview;
import hufs.likelion.gov.domain.review.repository.MemberReviewRepository;
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
    private final MemberRepository memberRepository;

    public GetMemberReviewsResponse getReviews(Authentication authentication){
        Member authMember = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        List<MemberReview> myReviews = memberReviewRepository.findByOwner(authMember);
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
        memberReviewRepository.save(newMemberReview);
        return PostMemberReviewResponse.builder().build();
    }
}