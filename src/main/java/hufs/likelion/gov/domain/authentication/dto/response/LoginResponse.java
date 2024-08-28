package hufs.likelion.gov.domain.authentication.dto.response;

import hufs.likelion.gov.domain.authentication.kakao.AuthTokens;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
	AuthTokens authTokens;
}
