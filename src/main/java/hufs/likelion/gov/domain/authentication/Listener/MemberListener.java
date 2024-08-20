package hufs.likelion.gov.domain.authentication.Listener;

import java.time.LocalDateTime;

import hufs.likelion.gov.domain.authentication.entity.Member;
import jakarta.persistence.PrePersist;

public class MemberListener {
	@PrePersist
	public void prePersist(Member member) {
		LocalDateTime now = LocalDateTime.now();
		member.setCreatedAt(now);
	}
}
