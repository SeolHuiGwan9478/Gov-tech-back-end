package hufs.likelion.gov.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hufs.likelion.gov.entity.Member;
import hufs.likelion.gov.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByToken(String token);

	void deleteByMember(Member member);

	void deleteByToken(String token);
}
