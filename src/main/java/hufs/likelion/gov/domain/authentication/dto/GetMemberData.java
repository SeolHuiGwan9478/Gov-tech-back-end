package hufs.likelion.gov.domain.authentication.dto;

import hufs.likelion.gov.domain.authentication.entity.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetMemberData {
    private Long id;
    private String memberId;
    private String email;
    private String profilePhoto;

    public static GetMemberData toGetMemberData(Member member) {
        return GetMemberData.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .profilePhoto(member.getProfilePhoto())
                .build();
    }
}
