package hufs.likelion.gov.domain.chatting.service;

import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.authentication.repository.MemberRepository;
import hufs.likelion.gov.domain.chatting.dto.DeleteChatRoomResponse;
import hufs.likelion.gov.domain.chatting.dto.FindChatRoomsData;
import hufs.likelion.gov.domain.chatting.dto.FindChatRoomsResponse;
import hufs.likelion.gov.domain.chatting.entity.ChatRoom;
import hufs.likelion.gov.domain.chatting.entity.ChatRoomMember;
import hufs.likelion.gov.domain.chatting.repository.ChatRoomMemberRepository;
import hufs.likelion.gov.domain.chatting.repository.ChatRoomRepository;
import hufs.likelion.gov.domain.matching.entity.CarePost;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hufs.likelion.gov.global.constant.GlobalConstant.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MemberRepository memberRepository;

    public FindChatRoomsResponse findChatRooms(Authentication authentication){
        Member authMember = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        List<ChatRoomMember> chatRoomMembers = chatRoomMemberRepository.findByMember(authMember);
        List<FindChatRoomsData> findChatRoomsData = chatRoomMembers.stream().map((chatRoomMember) -> {
            ChatRoom chatRoom = chatRoomMember.getChatRoom();
            CarePost carePost = chatRoom.getCarePost();
            return FindChatRoomsData.builder()
                    .id(chatRoom.getId())
                    .roomId(chatRoom.getRoomId())
                    .title(carePost.getTitle())
                    .price(carePost.getPrice())
                    .address(carePost.getAddress())
                    .build();
        }).toList();
        return FindChatRoomsResponse.builder()
                .totalCounts(findChatRoomsData.size())
                .data(findChatRoomsData)
                .build();
    }

    @Transactional
    public DeleteChatRoomResponse deleteChatRoom(String roomId, Authentication authentication){
        Member authMember = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_CHAT_ROOM_ERR_MSG));
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndMember(chatRoom, authMember)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_CHAT_ROOM_MEMBER_ERR_MSG));
        chatRoomMemberRepository.delete(chatRoomMember);
        chatRoom.downMemberCount();
        if(chatRoom.getMemberCount() == 0){
            chatRoomRepository.delete(chatRoom);
        }
        return DeleteChatRoomResponse.builder()
                .roomId(roomId)
                .memberId(authMember.getId())
                .build();
    }
}