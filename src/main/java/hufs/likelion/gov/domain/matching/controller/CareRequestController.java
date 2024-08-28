package hufs.likelion.gov.domain.matching.controller;

import hufs.likelion.gov.domain.matching.dto.CareRequestResponse;
import hufs.likelion.gov.domain.matching.dto.PostCareRequestRequest;
import hufs.likelion.gov.domain.matching.service.CareRequestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/care/requests")
@RequiredArgsConstructor
public class CareRequestController {
    private final CareRequestService careRequestService;
    //요청 생성
    @PostMapping
    public ResponseEntity<?> createCareRequest(Authentication authentication, @RequestBody PostCareRequestRequest request){
        careRequestService.createCareRequest(authentication, request);
        return ResponseEntity.ok().build();
    }
    //요청 조회
    @GetMapping
    public ResponseEntity<?> findAllCareRequest(Authentication authentication){
        List<CareRequestResponse> responses = careRequestService.findAllCareRequest(authentication);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
