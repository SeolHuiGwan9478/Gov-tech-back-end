package hufs.likelion.gov.domain.authentication.kakao.interfaces;

import org.springframework.util.MultiValueMap;

import hufs.likelion.gov.domain.authentication.kakao.OAuthProvider;

public interface OAuthLoginParams {
	OAuthProvider oAuthProvider();
	MultiValueMap<String, String> makeBody();
}