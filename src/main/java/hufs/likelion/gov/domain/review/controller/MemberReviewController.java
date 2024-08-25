package hufs.likelion.gov.domain.review.controller;

import hufs.likelion.gov.domain.review.dto.GetMemberReviewsResponse;
import hufs.likelion.gov.domain.review.dto.PostMemberReviewRequest;
import hufs.likelion.gov.domain.review.service.MemberReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class MemberReviewController {
    private final MemberReviewService memberReviewService;

    @GetMapping
    public ResponseEntity<?> getReviews(Authentication authentication){
        log.info("Request to get reviews");
        GetMemberReviewsResponse response = memberReviewService.getReviews(authentication);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postReviews(Authentication authentication, PostMemberReviewRequest request){
        log.info("Request to post reviews");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}