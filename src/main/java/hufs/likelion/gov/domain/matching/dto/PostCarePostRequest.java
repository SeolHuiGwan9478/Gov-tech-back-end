package hufs.likelion.gov.domain.matching.dto;

import hufs.likelion.gov.domain.matching.entity.CarePostType;
import lombok.Data;

import java.util.List;
@Data
public class PostCarePostRequest {
    private String title;
    private String address;
    private int price;
    private String content;
    private CarePostType type;
    private List<PostCareBabyInCarePostRequest> babies;
}