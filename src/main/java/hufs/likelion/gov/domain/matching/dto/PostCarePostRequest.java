package hufs.likelion.gov.domain.matching.dto;

import lombok.Data;

@Data
public class PostCarePostRequest {
    private String title;
    private String address;
    private int price;
    private String content;
}