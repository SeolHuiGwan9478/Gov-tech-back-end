package hufs.likelion.gov.domain.chatting.dto;

import hufs.likelion.gov.domain.chatting.entity.Message;
import hufs.likelion.gov.domain.chatting.entity.MessageStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponse {
    private String roomId;
    private Long senderId;
    private String senderName;
    private String message;
    private MessageStatus status;
    private LocalDateTime date;

    public MessageResponse(Message message){
        this.roomId = message.getRoomId();
        this.senderId = message.getSenderId();
        this.senderName = message.getSenderName();
        this.message = message.getMessage();
        this.status = message.getStatus();
        this.date = message.getDate();
    }
}
