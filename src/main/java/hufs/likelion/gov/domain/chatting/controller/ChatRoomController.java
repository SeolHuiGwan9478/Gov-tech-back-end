package hufs.likelion.gov.domain.chatting.controller;

import hufs.likelion.gov.domain.chatting.dto.DeleteChatRoomResponse;
import hufs.likelion.gov.domain.chatting.dto.GetChatRoomsResponse;
import hufs.likelion.gov.domain.chatting.dto.PostChatRoomRequest;
import hufs.likelion.gov.domain.chatting.dto.PostChatRoomResponse;
import hufs.likelion.gov.domain.chatting.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatrooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping
    public ResponseEntity<?> getChatRooms(Authentication authentication){
        log.info("Request to get chatRooms");
        GetChatRoomsResponse response = chatRoomService.findChatRooms(authentication);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postChatRoom(Authentication authentication, PostChatRoomRequest request){
        log.info("Request to post chatRoom");
        PostChatRoomResponse response = chatRoomService.createChatRoom(authentication, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<?> deleteChatRoom(Authentication authentication, @PathVariable("roomId") String roomId){
        log.info("Request to delete chatRoom-{}", roomId);
        DeleteChatRoomResponse response = chatRoomService.deleteChatRoom(roomId, authentication);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}