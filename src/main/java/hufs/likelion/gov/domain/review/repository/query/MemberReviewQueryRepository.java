package hufs.likelion.gov.domain.review.repository.query;

import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.review.entity.MemberReview;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberReviewQueryRepository {
    private final EntityManager em;

    public List<MemberReview> findReviewsByOwner(Member owner){
        String jpql = "select distinct mr from MemberReview mr" +
                " join fetch mr.keywords" +
                " where mr.owner = :owner";
        return em.createQuery(jpql, MemberReview.class)
                .setParameter("owner", owner)
                .getResultList();
    }
}
