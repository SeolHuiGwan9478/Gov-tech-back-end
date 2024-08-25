package hufs.likelion.gov.domain.review.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetMemberReviewsResponse {
    private int totalCounts;
    private List<GetMemberReviewsData> data;
}
