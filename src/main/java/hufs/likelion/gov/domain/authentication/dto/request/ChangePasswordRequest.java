package hufs.likelion.gov.domain.authentication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordRequest {
	private String memberId;
	private String oldPassword;
	private String newPassword;
}
