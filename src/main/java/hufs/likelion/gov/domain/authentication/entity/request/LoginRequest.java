package hufs.likelion.gov.domain.authentication.entity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

	private String username;
	private String password;

	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
