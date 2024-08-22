package hufs.likelion.gov.domain.chatting.repository;

import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.chatting.entity.ChatRoom;
import hufs.likelion.gov.domain.chatting.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    @EntityGraph(attributePaths = {"chatRoom.carePost"})
    List<ChatRoomMember> findByMember(Member member);
    Optional<ChatRoomMember> findByChatRoomAndMember(ChatRoom chatRoom, Member member);
}