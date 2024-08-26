package hufs.likelion.gov.domain.complain.dto;

import hufs.likelion.gov.domain.authentication.dto.GetMemberData;
import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.complain.entity.ComplainReply;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetComplainRepliesData {
    private Long id;
    private GetMemberData member;
    private String content;
    private String createdAt;

    public static GetComplainRepliesData toGetComplainRepliesData(ComplainReply complainReply){
        Member member = complainReply.getMember();
        return GetComplainRepliesData.builder()
                .id(complainReply.getId())
                .member(GetMemberData.toGetMemberData(member))
                .content(complainReply.getContent())
                .createdAt(complainReply.getCreatedAt().toString())
                .build();
    }
}
