package hufs.likelion.gov.domain.matching.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCarePostsResponse {
	private int totalPages;
	private int curPage;
	private List<GetCarePostsData> data;

	@Override
	public String toString() {
		return "GetCarePostsResponse{" +
			"totalPages=" + totalPages +
			", curPage=" + curPage +
			", data=" + data +
			'}';
	}
}