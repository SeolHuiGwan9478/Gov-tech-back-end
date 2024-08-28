package hufs.likelion.gov.domain.matching.controller;

import hufs.likelion.gov.domain.matching.dto.*;
import hufs.likelion.gov.domain.matching.service.CarePostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/care/posts")
@RequiredArgsConstructor
public class CarePostController {
    private final CarePostService carePostService;
    @GetMapping
    public ResponseEntity<?> getCarePosts(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        log.info("Request to get care post list");
        GetCarePostsResponse response = carePostService.findCarePosts(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postCarePost(Authentication authentication, @RequestBody PostCarePostRequest request) {
        log.info("Request to post care post");
        PostCarePostResponse response = carePostService.createCarePost(authentication, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> putCarePost(Authentication authentication, @PathVariable("postId") Long postId, @RequestBody PutCarePostRequest request){
        log.info("Request to put care post");
        PutCarePostResponse response = carePostService.updateCarePost(authentication, postId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getCarePost(@PathVariable("postId") Long postId){
        log.info("Request to get care post {}", postId);
        GetCarePostResponse response = carePostService.findCarePost(postId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deleteCarePost(Authentication authentication, @PathVariable("postId") Long postId){
        log.info("Request to delete care post {}", postId);
        carePostService.deleteCarePost(authentication, postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{postId}/finish")
    public ResponseEntity<?> finishCarePost(Authentication authentication, @PathVariable("postId") Long postId){
        log.info("Request to finish care post {}", postId);
        PatchCarePostResponse response = carePostService.finishCarePost(authentication, postId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //요청 수락
    @PatchMapping("/accept/{postId}/{requestId}") //patch
    public ResponseEntity<?> acceptCareRequest(Authentication authentication, @PathVariable("postId") Long postId, @PathVariable Long requestId) {
        CareRequestResponse response = carePostService.acceptCareRequest(authentication, postId, requestId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //요청 거부
    @PatchMapping("/reject/{postId}/{requestId}")
    public ResponseEntity<?> rejectCareRequest(Authentication authentication, @PathVariable("postId") Long postId,  @PathVariable Long requestId) {
        CareRequestResponse response = carePostService.rejectCareRequest(authentication, postId, requestId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
