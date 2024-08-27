package hufs.likelion.gov.domain.complain.dto;

import hufs.likelion.gov.domain.authentication.dto.request.GetMemberData;
import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.complain.entity.ComplainReply;
import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@Builder
public class GetComplainRepliesData {
    private Long id;
    private GetMemberData member;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GetComplainRepliesData toGetComplainRepliesData(ComplainReply complainReply){
        Member member = complainReply.getMember();
        return GetComplainRepliesData.builder()
                .id(complainReply.getId())
                .member(GetMemberData.toGetMemberData(member))
                .content(complainReply.getContent())
                .createdAt(complainReply.getCreatedAt())
                .updatedAt(complainReply.getUpdatedAt())
                .build();
    }
}
