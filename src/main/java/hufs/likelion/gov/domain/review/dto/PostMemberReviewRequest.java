package hufs.likelion.gov.domain.review.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostMemberReviewRequest {
    private String ownerId;
    private String content;
    private int score;
    private List<String> keywords;
}