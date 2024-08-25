package hufs.likelion.gov.domain.matching.entity;
import hufs.likelion.gov.domain.authentication.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CarePost {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK
    private String title; // 제목
    private String content; // 내용
    private int price; // 시급
    private String address; // 주소
    private CarePostType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @CreatedDate
    private LocalDateTime createdAt; // 생성일
    @LastModifiedDate
    private LocalDateTime updatedAt; // 수정일
}