package hufs.likelion.gov.domain.recommend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import hufs.likelion.gov.domain.matching.dto.GetCarePostsData;
import hufs.likelion.gov.domain.matching.service.CarePostService;
import hufs.likelion.gov.domain.recommend.dto.ChatGPTResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendService {

	private final CarePostService carePostService;

	@Value("${openai.model}")
	private String model;
	@Value("${openai.api.url}")
	private String apiURL;
	@Value("${openai.secret.key}")
	private String secret_key;

	public String recommend() {
		List<String> carePosts = carePostService.findCarePosts(
				PageRequest.of(0, 100, Sort.by("createdAt").ascending())).getData().stream()
			.map(GetCarePostsData::toString)
			.toList();

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(secret_key);

		String requestBody = "{\n" +
			"  \"model\": \"" + model + "\",\n" +
			"  \"messages\": [\n" +
			"    {\"role\": \"system\", \"content\": \"날짜를 바탕으로 계절이나 다른 요인들을 계산해서 어느 지역, 몇시에 가장 요청이 많을지 예측하고, 가격이 높은 지역도 고려해서 추천해주어야 한다.\"},\n"
			+
			"    {\"role\": \"user\", \"content\": \"" + carePosts + "\"}\n" +
			"  ],\n" +
			"  \"max_tokens\": 1000,\n" +
			"  \"temperature\": 0.1\n" +
			"}";

		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
		ChatGPTResponse response = restTemplate.postForObject(apiURL, entity, ChatGPTResponse.class);
		assert response != null;
		return response.getChoices().get(0).getMessage().getContent();

	}
}
