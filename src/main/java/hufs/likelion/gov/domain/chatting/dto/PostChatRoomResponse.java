package hufs.likelion.gov.domain.chatting.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostChatRoomResponse {
    private String roomId;
}