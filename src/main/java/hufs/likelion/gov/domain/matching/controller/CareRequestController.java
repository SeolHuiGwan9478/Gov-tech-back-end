package hufs.likelion.gov.domain.matching.controller;

import hufs.likelion.gov.domain.matching.dto.PostCareRequestRequest;
import hufs.likelion.gov.domain.matching.service.CareRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/care/request")
@RequiredArgsConstructor
public class CareRequestController {
    private final CareRequestService careRequestService;
    //요청 생성
    @PostMapping
    public ResponseEntity<Void> createCareRequest(Authentication authentication, @RequestBody PostCareRequestRequest request){
        careRequestService.createCareRequest(authentication, request);
        return ResponseEntity.ok().build();
    }
    //요청 조회
    @GetMapping("/{requestId}")
    public ResponseEntity<Void> findAllCareRequest(Authentication authentication){
        careRequestService.findAllCareRequest(authentication);
        return ResponseEntity.ok().build();
    }

    //요청 수락
    @PutMapping("/accept/{requestId}")
    public ResponseEntity<Void> acceptCareRequest(Authentication authentication, @RequestBody PostCareRequestRequest request) {
        careRequestService.acceptCareRequest(authentication, request);
        return ResponseEntity.ok().build();
    }
    //요청 거부
    @PutMapping("/reject/{requestId}")
    public ResponseEntity<Void> rejectCareRequest(Authentication authentication, @RequestBody PostCareRequestRequest request) {
        careRequestService.rejectCareRequest(authentication, request);
        return ResponseEntity.ok().build();
    }
}
