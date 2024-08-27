package hufs.likelion.gov.domain.authentication.kakao;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import hufs.likelion.gov.domain.authentication.kakao.interfaces.OAuthLoginParams;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoLoginParams implements OAuthLoginParams {

	private String authorizationCode;

	@Override
	public OAuthProvider oAuthProvider() {
		return OAuthProvider.KAKAO;
	}

	@Override
	public MultiValueMap<String, String> makeBody() {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("code", authorizationCode);
		return body;
	}
}