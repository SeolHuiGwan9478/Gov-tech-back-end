package hufs.likelion.gov.domain.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hufs.likelion.gov.domain.authentication.entity.RefreshToken;
import hufs.likelion.gov.domain.authentication.entity.Member;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByToken(String token);

	void deleteByMember(Member member);

	void deleteByToken(String token);

	Optional<RefreshToken> findByMember_MemberId(String member_memberId);
}
