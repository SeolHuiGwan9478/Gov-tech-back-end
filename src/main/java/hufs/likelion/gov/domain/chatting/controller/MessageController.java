package hufs.likelion.gov.domain.chatting.controller;

import hufs.likelion.gov.domain.chatting.dto.MessageRequest;
import hufs.likelion.gov.domain.chatting.dto.MessageResponse;
import hufs.likelion.gov.domain.chatting.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final MessageService messageService;
    private final String CHAT_SUB_PATH = "/sub/chatroom";

    @MessageMapping("/message")
    private void sendMessage(@Payload MessageRequest message){
        log.info("Request to send message");
        MessageResponse responseMessage = messageService.saveMessage(message);
        simpMessageSendingOperations.convertAndSend(CHAT_SUB_PATH + responseMessage.getRoomId(), responseMessage);
    }
}
