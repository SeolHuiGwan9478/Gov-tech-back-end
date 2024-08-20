package hufs.likelion.gov.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import hufs.likelion.gov.Listener.MemberListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	public Member(String memberId, String password, String email, String profilePhoto, Role role) {
		this.memberId = memberId;
		this.password = password;
		this.email = email;
		this.profilePhoto = profilePhoto;
		this.role = role;
	}

	public Member() {

	}
}
