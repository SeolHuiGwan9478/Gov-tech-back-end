package hufs.likelion.gov.domain.complain.dto;

import hufs.likelion.gov.domain.authentication.dto.GetMemberData;
import hufs.likelion.gov.domain.complain.entity.ComplainStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GetComplainResponse {
    private Long id;
    private String title;
    private String type;
    private String content;
    private ComplainStatus status;
    private GetMemberData member;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
