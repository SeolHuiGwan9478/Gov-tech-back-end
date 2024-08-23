package hufs.likelion.gov.domain.chatting.dto;

import hufs.likelion.gov.domain.chatting.entity.MessageStatus;
import lombok.Data;

@Data
public class MessageRequest {
    private String message;
    private String roomId;
    private MessageStatus status;
}