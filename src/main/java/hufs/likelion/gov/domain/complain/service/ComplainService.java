package hufs.likelion.gov.domain.complain.service;

import hufs.likelion.gov.domain.complain.dto.GetComplainsData;
import hufs.likelion.gov.domain.complain.dto.GetComplainsResponse;
import hufs.likelion.gov.domain.complain.entity.Complain;
import hufs.likelion.gov.domain.complain.repository.ComplainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ComplainService {
    private final ComplainRepository complainRepository;

    public GetComplainsResponse findComplains(Pageable pageable){
        Page<Complain> findComplains = complainRepository.findAll(pageable);
        List<GetComplainsData> complainsData = findComplains.getContent().stream().map(GetComplainsData::toGetComplainsData).toList();
        return GetComplainsResponse.builder()
                .totalPages(findComplains.getTotalPages())
                .curPage(findComplains.getNumber())
                .complains(complainsData)
                .build();
    }
}
