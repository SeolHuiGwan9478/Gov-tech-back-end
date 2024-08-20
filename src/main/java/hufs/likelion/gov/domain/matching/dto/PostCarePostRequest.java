package hufs.likelion.gov.domain.matching.dto;

import hufs.likelion.gov.domain.matching.entity.CarePostType;
import lombok.Data;

@Data
public class PostCarePostRequest {
    private String title;
    private String address;
    private int price;
    private String content;
    private CarePostType type;
}