package hufs.likelion.gov.domain.matching.dto;

import hufs.likelion.gov.domain.matching.entity.CareRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CareRequestResponse {
    private Long id;
    private Long carePostId; // 요청한 게시글 id
    private Long requesterId; // 요청자 id
    private String status; // 상태
    private String createdAt; // 생성일

    public static CareRequestResponse toCareRequestResponse(CareRequest careRequest){
        return CareRequestResponse.builder()
                .id(careRequest.getId())
                .carePostId(careRequest.getCarePost().getId())
                .requesterId(careRequest.getRequester().getId())
                .status(careRequest.getStatus().name())
                .createdAt(careRequest.getCreatedAt().toString())
                .build();
    }
}
