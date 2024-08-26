package hufs.likelion.gov.domain.complain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetComplainsResponse {
    private int totalPages;
    private int curPage;
    private List<GetComplainsData> complains;
}
