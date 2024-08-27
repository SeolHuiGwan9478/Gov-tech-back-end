package hufs.likelion.gov.domain.authentication.kakao.interfaces;

import hufs.likelion.gov.domain.authentication.kakao.OAuthProvider;

public interface OAuthApiClient {
	OAuthProvider oAuthProvider();
	String requestAccessToken(OAuthLoginParams params);
	OAuthInfoResponse requestOauthInfo(String accessToken);
}