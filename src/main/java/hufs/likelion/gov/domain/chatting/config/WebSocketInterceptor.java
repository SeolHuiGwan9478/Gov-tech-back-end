package hufs.likelion.gov.domain.chatting.config;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.Map;

import static hufs.likelion.gov.global.constant.GlobalConstant.NOT_FOUND_AUTH_HEADER_ERR_MSG;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebSocketInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        boolean isCommonMessage = handleMessage(accessor);
        if(isCommonMessage){
            return message;
        }
        return null;
    }

    private boolean handleMessage(StompHeaderAccessor accessor){
        try {
            if (StompCommand.CONNECT.equals(accessor.getCommand())) { // command == CONNECT
                Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
                String authorization = accessor.getFirstNativeHeader("Authorization");
                if(authorization == null) throw new IllegalArgumentException(NOT_FOUND_AUTH_HEADER_ERR_MSG);
                // write parsing jwt access token
                accessor.setSessionAttributes(sessionAttributes);
                return true;
            } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) { // command == DISCONNECT
                Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
                // write update disconnected at
                return true;
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
