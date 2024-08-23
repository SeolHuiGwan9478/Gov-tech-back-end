package hufs.likelion.gov.domain.chatting.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetChatRoomsResponse {
    private int totalCounts;
    private List<GetChatRoomsData> data;
}
