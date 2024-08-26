package hufs.likelion.gov.domain.complain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetComplainRepliesResponse {
    private int totalCount;
    private List<GetComplainRepliesData> data;
}