package hufs.likelion.gov.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberReviewKeyword {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String keyword;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_review_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MemberReview memberReview;
}