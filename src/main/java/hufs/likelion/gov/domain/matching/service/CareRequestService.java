package hufs.likelion.gov.domain.matching.service;

import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.authentication.repository.MemberRepository;
import hufs.likelion.gov.domain.matching.dto.PostCareRequestRequest;
import hufs.likelion.gov.domain.matching.entity.CarePost;
import hufs.likelion.gov.domain.matching.entity.CareRequest;
import hufs.likelion.gov.domain.matching.entity.MatchStatus;
import hufs.likelion.gov.domain.matching.repository.CarePostRepository;
import hufs.likelion.gov.domain.matching.repository.CareRequestRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor

public class CareRequestService {
    private final CareRequestRepository careRequestRepository;
    private final MemberRepository memberRepository;
    private final CarePostRepository carePostRepository;

    //요청 생성
    public void createCareRequest(Authentication authentication, PostCareRequestRequest request){
        Member requester = memberRepository.findByMemberId(authentication.getName()).orElseThrow();
        CarePost carePost = carePostRepository.findById(request.getCarePostId()).orElseThrow();

        CareRequest careRequest = new CareRequest(requester, carePost);
        careRequestRepository.save(careRequest);
    }
    //모든 요청 조회
    public List<CareRequest> findAllCareRequest(Authentication authentication) {
        Member requester = memberRepository.findByMemberId(authentication.getName()).orElseThrow();
        return careRequestRepository.findByRequester(requester);
    }


    //요청 수락
    public void acceptCareRequest(Authentication authentication, PostCareRequestRequest request) {
        Member requester = memberRepository.findByMemberId(authentication.getName()).orElseThrow();
        CarePost carePost = carePostRepository.findById(request.getCarePostId()).orElseThrow();
        CareRequest careRequest = careRequestRepository.findByRequesterAndCarePost(requester, carePost);

        if(careRequest.getStatus() == MatchStatus.REQUESTED){
            careRequest.setStatus(MatchStatus.ACCEPTED);
            careRequest.setCreatedAt(LocalDateTime.now());
        } else {
            throw new AccessDeniedException("이미 수락된 요청입니다.");
        }
    }

    //요청 거부
    public void rejectCareRequest(Authentication authentication, PostCareRequestRequest request) {
        Member requester = memberRepository.findByMemberId(authentication.getName()).orElseThrow();
        CarePost carePost = carePostRepository.findById(request.getCarePostId()).orElseThrow();
        CareRequest careRequest = careRequestRepository.findByRequesterAndCarePost(requester, carePost);

        if(careRequest.getStatus() == MatchStatus.REQUESTED){
            careRequest.setStatus(MatchStatus.REJECTED);
            careRequest.setCreatedAt(LocalDateTime.now());
        } else {
            throw new AccessDeniedException("이미 수락된 요청입니다.");
        }
    }
}
