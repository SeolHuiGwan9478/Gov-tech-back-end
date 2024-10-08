package hufs.likelion.gov.domain.complain.controller;

import hufs.likelion.gov.domain.complain.dto.*;
import hufs.likelion.gov.domain.complain.service.ComplainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/complains")
public class ComplainController {
    private final ComplainService complainService;

    @GetMapping
    public ResponseEntity<?> getComplains(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable){
        log.info("Request to get complains");
        GetComplainsResponse response = complainService.findComplains(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postComplains(Authentication authentication, @RequestBody PostComplainRequest request){
        log.info("Request to post complain");
        PostComplainResponse response = complainService.createComplain(authentication, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{complainId}")
    public ResponseEntity<?> updateComplain(Authentication authentication, @PathVariable("complainId") Long complainId, @RequestBody PutComplainRequest request){
        log.info("Request to put complain {}", complainId);
        PutComplainResponse response = complainService.updateComplain(authentication, complainId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{complainId}")
    public ResponseEntity<?> getComplain(@PathVariable("complainId") Long complainId){
        log.info("Request to get complain {}", complainId);
        GetComplainResponse response = complainService.getComplain(complainId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{complainId}/replies")
    public ResponseEntity<?> getComplainReplies(@PathVariable("complainId") Long complainId){
        log.info("Request to get complain replies {}", complainId);
        GetComplainRepliesResponse response = complainService.getComplainReplies(complainId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{complainId}/replies")
    public ResponseEntity<?> postComplainReplies(Authentication authentication, @PathVariable("complainId") Long complainId, @RequestBody PostComplainReplyRequest request) {
        log.info("Request to post complain reply {}", complainId);
        PostComplainReplyResponse response = complainService.createComplainReply(authentication, complainId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}