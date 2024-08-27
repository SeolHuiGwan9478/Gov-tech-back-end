package hufs.likelion.gov.domain.authentication.kakao;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import hufs.likelion.gov.domain.authentication.kakao.interfaces.OAuthInfoResponse;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfoResponse implements OAuthInfoResponse {

	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	@JsonProperty("id")
	private Long id;

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class KakaoAccount {
		private KakaoProfile profile;
		private String email;
	}

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class KakaoProfile {
		private String nickname;
	}

	@Override
	public String getEmail() {
		return kakaoAccount.email;
	}

	@Override
	public String getNickname() {
		return kakaoAccount.profile.nickname;
	}

	@Override
	public OAuthProvider getOAuthProvider() {
		return OAuthProvider.KAKAO;
	}
}