package hufs.likelion.gov.domain.chatting.service;

import hufs.likelion.gov.domain.chatting.dto.MessageRequest;
import hufs.likelion.gov.domain.chatting.dto.MessageResponse;
import hufs.likelion.gov.domain.chatting.entity.Message;
import hufs.likelion.gov.domain.chatting.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    public MessageResponse saveMessage(MessageRequest dto){
        Message message = Message.builder()
                .message(dto.getMessage())
                .status(dto.getStatus())
                .roomId(dto.getRoomId())
                .build();
        messageRepository.save(message);
        return new MessageResponse(message);
    }
}
