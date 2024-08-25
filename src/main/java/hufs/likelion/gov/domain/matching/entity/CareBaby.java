package hufs.likelion.gov.domain.matching.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
public class CareBaby {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int age;
    private String feature;
    private String history;
    private String etc;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CarePost carePost;
}
