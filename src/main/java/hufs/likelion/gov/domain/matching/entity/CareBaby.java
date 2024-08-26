package hufs.likelion.gov.domain.matching.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CareBaby {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int age;
    private String keyword;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CarePost carePost;
}
