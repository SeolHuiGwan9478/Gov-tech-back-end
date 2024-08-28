package hufs.likelion.gov.domain.matching.entity;
import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.matching.dto.PutCarePostRequest;
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
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CarePost {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK
    private String title; // 제목
    private String content; // 내용
    private int price; // 시급
    private String address; // 주소
    @Enumerated(EnumType.STRING)
    private CarePostType type;
    @Enumerated(EnumType.STRING)
    private CarePostStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @CreatedDate
    private LocalDateTime createdAt; // 생성일
    @LastModifiedDate
    private LocalDateTime updatedAt; // 수정일

    public void updateCarePost(PutCarePostRequest dto){
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.price = dto.getPrice();
        this.address = dto.getAddress();
    }

    public void finishCarePost(){
        this.status = CarePostStatus.MATCHED;
    }
}