package hufs.likelion.gov.domain.chatting.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FindChatRoomsResponse {
    private int totalCounts;
    private List<FindChatRoomsData> data;
}
