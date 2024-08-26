package hufs.likelion.gov.domain.complain.controller;

import hufs.likelion.gov.domain.complain.dto.GetComplainsResponse;
import hufs.likelion.gov.domain.complain.dto.PostComplainRequest;
import hufs.likelion.gov.domain.complain.dto.PostComplainResponse;
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

    @GetMapping("/{complainId}")
    public ResponseEntity<?> getComplain(@PathVariable("complainId") Long complainId){
        log.info("Request to get complain {}", complainId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}