package hufs.likelion.gov.domain.matching.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GetCarePostResponse {
    private Long id;
    private String title; // 제목
    private String content; // 내용
    private int price; // 시급
    private String address; // 주소
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime updatedAt; // 수정일
    private List<GetCareBabyInCarePostResponse> babies;
}
