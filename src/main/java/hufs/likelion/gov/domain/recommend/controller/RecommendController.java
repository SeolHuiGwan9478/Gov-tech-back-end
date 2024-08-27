package hufs.likelion.gov.domain.recommend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hufs.likelion.gov.domain.recommend.dto.RecommendResponse;
import hufs.likelion.gov.domain.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommend")
public class RecommendController {

	private final RecommendService recommendService;

	@GetMapping
	public ResponseEntity<?> recommend() {
		RecommendResponse response = new RecommendResponse(recommendService.recommend());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
