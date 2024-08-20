package hufs.likelion.gov.domain.matching.repository;

import hufs.likelion.gov.domain.matching.entity.CarePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarePostRepository extends JpaRepository<CarePost, Long> {
}
