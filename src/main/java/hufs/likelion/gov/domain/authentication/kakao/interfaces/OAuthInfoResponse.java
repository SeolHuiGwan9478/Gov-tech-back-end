package hufs.likelion.gov.domain.authentication.kakao.interfaces;

import hufs.likelion.gov.domain.authentication.kakao.OAuthProvider;

public interface OAuthInfoResponse {
	String getEmail();
	String getNickname();
	OAuthProvider getOAuthProvider();
}