package hufs.likelion.gov.domain.matching.service;

import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.authentication.repository.MemberRepository;
import hufs.likelion.gov.domain.matching.dto.*;
import hufs.likelion.gov.domain.matching.entity.CareBaby;

import hufs.likelion.gov.domain.matching.entity.CarePost;
import hufs.likelion.gov.domain.matching.entity.CarePostStatus;
import hufs.likelion.gov.domain.matching.repository.CareBabyRepository;
import hufs.likelion.gov.domain.matching.repository.CarePostRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hufs.likelion.gov.global.constant.GlobalConstant.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarePostService {
    private final CarePostRepository carePostRepository;
    private final CareBabyRepository careBabyRepository;
    private final MemberRepository memberRepository;


    public GetCarePostsResponse findCarePosts(Pageable pageable){
        // write authentication code
        Page<CarePost> carePostsPage = carePostRepository.findAll(pageable);
        List<GetCarePostsData> carePostsData = carePostsPage.getContent().stream()
                .map(GetCarePostsData::toCarePostsData).toList();
        return GetCarePostsResponse.builder()
                .totalPages(carePostsPage.getTotalPages())
                .curPage(carePostsPage.getNumber())
                .data(carePostsData)
                .build();
    }

    public GetCarePostResponse findCarePost(Long carePostId){
        // write authentication
        CarePost findCarePost = carePostRepository.findById(carePostId).orElseThrow(
                () -> new EntityNotFoundException(NOT_FOUND_CARE_POST_ERR_MSG)
        );
        List<CareBaby> findCareBabies = careBabyRepository.findByCarePost(findCarePost);
        List<GetCareBabyInCarePostResponse> babies = findCareBabies.stream()
                .map(GetCareBabyInCarePostResponse::toGetCareBabyInCarePostResponse)
                .toList();
        return GetCarePostResponse.builder()
                .id(findCarePost.getId())
                .title(findCarePost.getTitle())
                .content(findCarePost.getContent())
                .price(findCarePost.getPrice())
                .address(findCarePost.getAddress())
                .status(findCarePost.getStatus())
                .createdAt(findCarePost.getCreatedAt())
                .updatedAt(findCarePost.getUpdatedAt())
                .babies(babies)
                .build();
    }

    @Transactional
    public PostCarePostResponse createCarePost(Authentication authentication, PostCarePostRequest request){
        Member authMember = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        CarePost carePost = CarePost.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .address(request.getAddress())
                .price(request.getPrice())
                .status(CarePostStatus.WAITING)
                .type(request.getType())
                .member(authMember)
                .build();
        List<PostCareBabyInCarePostRequest> careBabyRequests = request.getBabies();
        List<CareBaby> careBabies = careBabyRequests.stream().map((careBabyRequest) -> CareBaby.builder()
                .age(careBabyRequest.getAge())
                .keyword(careBabyRequest.getKeyword())
                .carePost(carePost)
                .build()).toList();
        carePostRepository.save(carePost);
        careBabyRepository.saveAll(careBabies);
        return PostCarePostResponse.builder()
                .id(carePost.getId())
                .build();
    }

    @Transactional
    public PutCarePostResponse updateCarePost(Authentication authentication, Long carePostId, PutCarePostRequest request){
        Member authMember = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        CarePost findCarePost = carePostRepository.findById(carePostId).orElseThrow(
                () -> new EntityNotFoundException(NOT_FOUND_CARE_POST_ERR_MSG));
        findCarePost.updateCarePost(request); // post update
        List<CareBaby> existBabies = careBabyRepository.findByCarePost(findCarePost);
        careBabyRepository.deleteAll(existBabies);
        List<PutCareBabyInCarePostRequest> careBabyRequests = request.getBabies();
        List<CareBaby> careBabies = careBabyRequests.stream().map((careBabyRequest) -> CareBaby.builder()
                .age(careBabyRequest.getAge())
                .keyword(careBabyRequest.getKeyword())
                .carePost(findCarePost)
                .build()).toList();
        careBabyRepository.saveAll(careBabies); // care babies update
        return PutCarePostResponse.builder()
                .id(findCarePost.getId())
                .build();
    }

    @Transactional
    public void deleteCarePost(Authentication authentication, Long carePostId){
        Member authMember = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        CarePost findCarePost = carePostRepository.findById(carePostId).orElseThrow(
                () -> new EntityNotFoundException(NOT_FOUND_CARE_POST_ERR_MSG)
        );
        carePostRepository.delete(findCarePost);
    }

    @Transactional
    public PatchCarePostResponse finishCarePost(Authentication authentication, Long carePostId){
        Member authMember = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        CarePost findCarePost = carePostRepository.findById(carePostId).orElseThrow(
                () -> new EntityNotFoundException(NOT_FOUND_CARE_POST_ERR_MSG)
        );
        findCarePost.finishCarePost();
        return PatchCarePostResponse.builder()
                .id(findCarePost.getId())
                .build();
    }
}
