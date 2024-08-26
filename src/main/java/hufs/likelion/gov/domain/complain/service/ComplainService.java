package hufs.likelion.gov.domain.complain.service;

import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.authentication.repository.MemberRepository;
import hufs.likelion.gov.domain.complain.dto.GetComplainsData;
import hufs.likelion.gov.domain.complain.dto.GetComplainsResponse;
import hufs.likelion.gov.domain.complain.dto.PostComplainRequest;
import hufs.likelion.gov.domain.complain.dto.PostComplainResponse;
import hufs.likelion.gov.domain.complain.entity.Complain;
import hufs.likelion.gov.domain.complain.entity.ComplainStatus;
import hufs.likelion.gov.domain.complain.repository.ComplainRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hufs.likelion.gov.global.constant.GlobalConstant.NOT_FOUND_MEMBER_ERR_MSG;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ComplainService {
    private final ComplainRepository complainRepository;
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
}
