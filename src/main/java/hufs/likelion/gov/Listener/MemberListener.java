package hufs.likelion.gov.Listener;

import java.time.LocalDateTime;

import hufs.likelion.gov.entity.Member;
import jakarta.persistence.PrePersist;

public class MemberListener {
	@PrePersist
	public void prePersist(Member member) {
		LocalDateTime now = LocalDateTime.now();
		member.setCreatedAt(now);
	}
}
