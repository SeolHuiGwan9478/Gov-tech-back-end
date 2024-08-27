package hufs.likelion.gov.domain.authentication.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import hufs.likelion.gov.domain.authentication.kakao.interfaces.OAuthApiClient;
import hufs.likelion.gov.domain.authentication.kakao.interfaces.OAuthInfoResponse;
import hufs.likelion.gov.domain.authentication.kakao.interfaces.OAuthLoginParams;
import hufs.likelion.gov.domain.authentication.kakao.OAuthProvider;

@Component
public class RequestOAuthInfoService {
	private final Map<OAuthProvider, OAuthApiClient> clients;

	public RequestOAuthInfoService(List<OAuthApiClient> clients) {
		this.clients = clients.stream().collect(
			Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
		);
	}

	public OAuthInfoResponse request(OAuthLoginParams params) {
		OAuthApiClient client = clients.get(params.oAuthProvider());
		String accessToken = client.requestAccessToken(params);
		return client.requestOauthInfo(accessToken);
	}
}