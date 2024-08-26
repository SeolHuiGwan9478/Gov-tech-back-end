package hufs.likelion.gov.domain.complain.entity;

import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.complain.dto.PutComplainRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Complain {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String type;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;
    @Enumerated(EnumType.STRING)
    private ComplainStatus status;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void updateComplain(PutComplainRequest dto){
        this.title = dto.getTitle();
        this.type = dto.getType();
        this.content = dto.getContent();
    }

    public void updateStatus(){
        this.status = ComplainStatus.DONE;
    }
}