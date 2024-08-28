package hufs.likelion.gov.domain.authentication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpManagerRequest {
	private String userId;
	private String password;
	private String email;
}
