package hufs.likelion.gov.domain.matching.repository;

import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.matching.entity.CarePost;
import hufs.likelion.gov.domain.matching.entity.CareRequest;
import hufs.likelion.gov.domain.matching.entity.MatchStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CareRequestRepository extends JpaRepository<CareRequest, Long> {
    List<CareRequest> findByRequester(Member requester);

    CareRequest findByRequesterAndCarePost(Member requester, CarePost carePost);
}

