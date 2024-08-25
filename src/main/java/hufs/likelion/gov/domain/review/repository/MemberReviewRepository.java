package hufs.likelion.gov.domain.review.repository;

import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.review.entity.MemberReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberReviewRepository extends JpaRepository<MemberReview, Long> {
    List<MemberReview> findByOwner(Member member);
}
