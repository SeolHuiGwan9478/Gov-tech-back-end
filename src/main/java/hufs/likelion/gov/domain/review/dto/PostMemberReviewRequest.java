package hufs.likelion.gov.domain.review.dto;

import lombok.Data;

@Data
public class PostMemberReviewRequest {
    private String ownerId;
    private String content;
    private int score;
}