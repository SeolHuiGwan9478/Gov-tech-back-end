package hufs.likelion.gov.domain.complain.repository;

import hufs.likelion.gov.domain.complain.entity.Complain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplainRepository extends JpaRepository<Complain, Long> {
}
