package hufs.likelion.gov.domain.complain.service;

import hufs.likelion.gov.domain.authentication.dto.request.GetMemberData;
import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.authentication.repository.MemberRepository;
import hufs.likelion.gov.domain.complain.dto.*;
import hufs.likelion.gov.domain.complain.entity.Complain;
import hufs.likelion.gov.domain.complain.entity.ComplainReply;
import hufs.likelion.gov.domain.complain.entity.ComplainStatus;
import hufs.likelion.gov.domain.complain.repository.ComplainReplyRepository;
import hufs.likelion.gov.domain.complain.repository.ComplainRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hufs.likelion.gov.global.constant.GlobalConstant.NOT_FOUND_COMPLAIN_ERR_MSG;
import static hufs.likelion.gov.global.constant.GlobalConstant.NOT_FOUND_MEMBER_ERR_MSG;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ComplainService {
    private final ComplainRepository complainRepository;
    private final ComplainReplyRepository complainReplyRepository;
    private final MemberRepository memberRepository;

    public GetComplainsResponse findComplains(Pageable pageable){
        Page<Complain> findComplains = complainRepository.findAll(pageable);
        List<GetComplainsData> complainsData = findComplains.getContent().stream().map(GetComplainsData::toGetComplainsData).toList();
        return GetComplainsResponse.builder()
                .totalPages(findComplains.getTotalPages())
                .curPage(findComplains.getNumber())
                .complains(complainsData)
                .build();
    }

    @Transactional
    public PostComplainResponse createComplain(Authentication authentication, PostComplainRequest dto){
        Member authMember = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        Complain newComplain = Complain.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .type(dto.getType())
                .member(authMember)
                .status(ComplainStatus.WAITING)
                .build();
        complainRepository.save(newComplain);
        return PostComplainResponse.builder()
                .id(newComplain.getId())
                .build();
    }

    public GetComplainResponse getComplain(Long complainId){
        Complain findComplain = complainRepository.findById(complainId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_COMPLAIN_ERR_MSG));
        Member member = findComplain.getMember();
        return GetComplainResponse.builder()
                .id(findComplain.getId())
                .title(findComplain.getTitle())
                .content(findComplain.getContent())
                .type(findComplain.getType())
                .status(findComplain.getStatus())
                .member(GetMemberData.toGetMemberData(member))
                .createdAt(findComplain.getCreatedAt())
                .updatedAt(findComplain.getUpdatedAt())
                .build();
    }

    @Transactional
    public PutComplainResponse updateComplain(Authentication authentication, Long complainId, PutComplainRequest dto) {
        Member authMember = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        Complain findComplain = complainRepository.findById(complainId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_COMPLAIN_ERR_MSG));
        findComplain.updateComplain(dto);
        return PutComplainResponse.builder()
                .id(findComplain.getId())
                .build();
    }

    public GetComplainRepliesResponse getComplainReplies(Long complainId){
        Complain findComplain = complainRepository.findById(complainId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_COMPLAIN_ERR_MSG));
        List<ComplainReply> findComplainReplies = complainReplyRepository.findByComplain(findComplain);
        List<GetComplainRepliesData> repliesData = findComplainReplies.stream().map(GetComplainRepliesData::toGetComplainRepliesData).toList();
        return GetComplainRepliesResponse.builder()
                .totalCount(repliesData.size())
                .data(repliesData)
                .build();
    }

    @Transactional
    public PostComplainReplyResponse createComplainReply(Authentication authentication, Long complainId, PostComplainReplyRequest dto) {
        Member authMember = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        Complain findComplain = complainRepository.findById(complainId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_COMPLAIN_ERR_MSG));
        ComplainReply newComplainReply = ComplainReply.builder()
                .content(dto.getContent())
                .complain(findComplain)
                .member(authMember)
                .build();
        complainReplyRepository.save(newComplainReply);
        findComplain.updateStatus();
        return PostComplainReplyResponse.builder()
                .id(newComplainReply.getId())
                .build();
    }
}
