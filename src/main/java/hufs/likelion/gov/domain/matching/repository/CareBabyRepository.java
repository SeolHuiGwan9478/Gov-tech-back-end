package hufs.likelion.gov.domain.matching.repository;

import hufs.likelion.gov.domain.matching.entity.CareBaby;
import hufs.likelion.gov.domain.matching.entity.CarePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareBabyRepository extends JpaRepository<CareBaby, Long> {
    List<CareBaby> findByCarePost(CarePost carePost);
}