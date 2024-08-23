package hufs.likelion.gov.domain.chatting.service;

import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.authentication.repository.MemberRepository;
import hufs.likelion.gov.domain.chatting.dto.*;
import hufs.likelion.gov.domain.chatting.entity.ChatRoom;
import hufs.likelion.gov.domain.chatting.entity.ChatRoomMember;
import hufs.likelion.gov.domain.chatting.repository.ChatRoomMemberRepository;
import hufs.likelion.gov.domain.chatting.repository.ChatRoomRepository;
import hufs.likelion.gov.domain.chatting.repository.query.ChatRoomMemberQueryRepository;
import hufs.likelion.gov.domain.matching.entity.CarePost;
import hufs.likelion.gov.domain.matching.repository.CarePostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static hufs.likelion.gov.global.constant.GlobalConstant.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatRoomMemberQueryRepository chatRoomMemberQueryRepository;
    private final CarePostRepository carePostRepository;
    private final MemberRepository memberRepository;

    public GetChatRoomsResponse findChatRooms(Authentication authentication){
        Member authMember = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        List<ChatRoomMember> chatRoomMembers = chatRoomMemberRepository.findByMember(authMember);
        List<GetChatRoomsData> findChatRoomsData = chatRoomMembers.stream().map((chatRoomMember) -> {
            ChatRoom chatRoom = chatRoomMember.getChatRoom();
            CarePost carePost = chatRoom.getCarePost();
            return GetChatRoomsData.builder()
                    .id(chatRoom.getId())
                    .roomId(chatRoom.getRoomId())
                    .title(carePost.getTitle())
                    .price(carePost.getPrice())
                    .address(carePost.getAddress())
                    .build();
        }).toList();
        return GetChatRoomsResponse.builder()
                .totalCounts(findChatRoomsData.size())
                .data(findChatRoomsData)
                .build();
    }

    @Transactional
    public PostChatRoomResponse createChatRoom(Authentication authentication, PostChatRoomRequest dto){
        Member authMember = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        CarePost findCarePost = carePostRepository.findById(dto.getCarePostId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_CARE_POST_ERR_MSG));
        Optional<ChatRoomMember> chatRoomMember = chatRoomMemberQueryRepository.findByCarePostAndMember(findCarePost, authMember);
        if(chatRoomMember.isPresent()){
            return PostChatRoomResponse.builder()
                    .roomId(chatRoomMember.get().getChatRoom().getRoomId())
                    .build();
        }
        // create new chat room
        ChatRoom newChatRoom = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .carePost(findCarePost)
                .memberCount(2)
                .build();
        chatRoomRepository.save(newChatRoom);
        // create chat room member -> requester, receiver
        List<ChatRoomMember> newChatRoomMembers = List.of(
                ChatRoomMember.builder()
                        .chatRoom(newChatRoom)
                        .member(findCarePost.getMember())
                        .build(),
                ChatRoomMember.builder()
                        .chatRoom(newChatRoom)
                        .member(authMember)
                        .build()
        );
        chatRoomMemberRepository.saveAll(newChatRoomMembers);
        return PostChatRoomResponse.builder()
                .roomId(newChatRoom.getRoomId())
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