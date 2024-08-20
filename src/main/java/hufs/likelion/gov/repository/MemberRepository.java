package hufs.likelion.gov.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hufs.likelion.gov.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByMemberId(String memberId);

	Boolean existsByMemberId(String memberId);
}
