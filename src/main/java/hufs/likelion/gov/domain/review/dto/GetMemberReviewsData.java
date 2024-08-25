package hufs.likelion.gov.domain.review.dto;

import hufs.likelion.gov.domain.review.entity.MemberReview;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetMemberReviewsData {
    private Long id;
    private int score;
    private String content;
    private Long writerId;

    public static GetMemberReviewsData toGetMemberReviewsData(MemberReview review){
        return GetMemberReviewsData.builder()
                .id(review.getId())
                .score(review.getScore())
                .content(review.getContent())
                .writerId(review.getWriter().getId())
                .build();
    }
}