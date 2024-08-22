package hufs.likelion.gov.domain.chatting.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindChatRoomsData {
    private Long id;
    private String roomId;
    private String title;
    private String address;
    private int price;
    // add baby info
}