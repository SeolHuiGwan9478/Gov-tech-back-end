package hufs.likelion.gov.domain.complain.repository;

import hufs.likelion.gov.domain.complain.entity.Complain;
import hufs.likelion.gov.domain.complain.entity.ComplainReply;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplainReplyRepository extends JpaRepository<ComplainReply, Long> {
    @EntityGraph(attributePaths = {"member"})
    List<ComplainReply> findByComplain(Complain complain);
}