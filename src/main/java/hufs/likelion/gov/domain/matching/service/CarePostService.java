package hufs.likelion.gov.domain.matching.service;

import hufs.likelion.gov.domain.matching.dto.CarePostsData;
import hufs.likelion.gov.domain.matching.dto.CarePostsResponse;
import hufs.likelion.gov.domain.matching.entity.CarePost;
import hufs.likelion.gov.domain.matching.repository.CarePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarePostService {
    private final CarePostRepository carePostRepository;

    public CarePostsResponse findCarePosts(Pageable pageable){
        // write authentication code
        Page<CarePost> carePostsPage = carePostRepository.findAll(pageable);
        List<CarePostsData> carePostsData = carePostsPage.getContent().stream()
                .map(CarePostsData::toCarePostsData).toList();
        return CarePostsResponse.builder()
                .totalPages(carePostsPage.getTotalPages())
                .curPage(carePostsPage.getNumber())
                .data(carePostsData)
                .build();
    }
}
