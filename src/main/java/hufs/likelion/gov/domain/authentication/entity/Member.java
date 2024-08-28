package hufs.likelion.gov.domain.authentication.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import hufs.likelion.gov.domain.authentication.Listener.MemberListener;
import hufs.likelion.gov.domain.authentication.kakao.OAuthProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@EntityListeners(MemberListener.class)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String memberId;
	private String password;
	private String email;
	private Boolean isAuth = false;
	private String profilePhoto;
	private Role role;
	private String tid;

	@Enumerated(EnumType.STRING)
	private OAuthProvider oAuthProvider;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@Builder
	public Member(String memberId, String password, String email, String profilePhoto, Role role, OAuthProvider oAuthProvider) {
		this.memberId = memberId;
		this.password = password;
		this.email = email;
		this.profilePhoto = profilePhoto;
		this.role = role;
		this.oAuthProvider = oAuthProvider;
	}

	public Member() {

	}

	public void updateTid(String tid) {
		this.tid = tid;
	}
}
