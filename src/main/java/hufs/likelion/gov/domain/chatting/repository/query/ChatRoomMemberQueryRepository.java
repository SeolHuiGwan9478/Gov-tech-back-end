package hufs.likelion.gov.domain.chatting.repository.query;

import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.chatting.entity.ChatRoomMember;
import hufs.likelion.gov.domain.matching.entity.CarePost;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatRoomMemberQueryRepository {
    private final EntityManager em;

    public Optional<ChatRoomMember> findByCarePostAndMember(CarePost carePost, Member member){
        String jpql = "select crm from ChatRoomMember crm " +
                "join fetch crm.chatRoom cr " +
                "where cr.carePost = :carePost " +
                " and crm.member = :member";
        try{
            return Optional.of(em.createQuery(jpql, ChatRoomMember.class)
                    .setParameter("carePost", carePost)
                    .setParameter("member", member)
                    .getSingleResult());
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
