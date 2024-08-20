package hufs.likelion.gov.domain.matching.dto;

import hufs.likelion.gov.domain.matching.entity.CarePost;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetCarePostsData {
    private Long id;
    private String title;
    private String address;
    // baby features
    private int price;

    public static GetCarePostsData toCarePostsData(CarePost carePost){
        return GetCarePostsData.builder()
                .id(carePost.getId())
                .title(carePost.getTitle())
                .address(carePost.getAddress())
                .price(carePost.getPrice())
                .build();
    }
}
