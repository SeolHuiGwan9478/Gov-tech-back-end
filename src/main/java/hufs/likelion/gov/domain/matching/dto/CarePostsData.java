package hufs.likelion.gov.domain.matching.dto;

import hufs.likelion.gov.domain.matching.entity.CarePost;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarePostsData {
    private Long id;
    private String title;
    private String address;
    // baby features
    private int price;

    public static CarePostsData toCarePostsData(CarePost carePost){
        return CarePostsData.builder()
                .id(carePost.getId())
                .title(carePost.getTitle())
                .address(carePost.getAddress())
                .price(carePost.getPrice())
                .build();
    }
}
