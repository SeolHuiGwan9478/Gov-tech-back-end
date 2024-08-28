package hufs.likelion.gov.domain.complain.dto;

import hufs.likelion.gov.domain.authentication.dto.request.GetMemberData;
import hufs.likelion.gov.domain.complain.entity.Complain;
import hufs.likelion.gov.domain.complain.entity.ComplainStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GetComplainsData {
    private Long id;
    private String title;
    private String type;
    private String content;
    private ComplainStatus status;
    private GetMemberData member;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GetComplainsData toGetComplainsData(Complain complain){
        GetMemberData member = GetMemberData.toGetMemberData(complain.getMember());
        return GetComplainsData.builder()
                .id(complain.getId())
                .title(complain.getTitle())
                .type(complain.getType())
                .content(complain.getContent())
                .status(complain.getStatus())
                .member(member)
                .createdAt(complain.getCreatedAt())
                .updatedAt(complain.getUpdatedAt())
                .build();
    }
}