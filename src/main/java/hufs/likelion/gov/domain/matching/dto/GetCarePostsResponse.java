package hufs.likelion.gov.domain.matching.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetCarePostsResponse {
    private int totalPages;
    private int curPage;
    private List<GetCarePostsData> data;
}