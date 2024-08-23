package hufs.likelion.gov.domain.chatting.entity;

import hufs.likelion.gov.domain.matching.entity.CarePost;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomId; // 채팅방 ID
    private int memberCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CarePost carePost;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "chatRoom", orphanRemoval = true)
    private List<ChatRoomMember> chatRoomMembers;

    public void downMemberCount() {
        this.memberCount -= 1;
    }
    public void upMemberCount() {this.memberCount += 1;}
}
