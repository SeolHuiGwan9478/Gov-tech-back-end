package hufs.likelion.gov.domain.matching.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CarePostsResponse {
    private int totalPages;
    private int curPage;
    private List<CarePostsData> data;
}