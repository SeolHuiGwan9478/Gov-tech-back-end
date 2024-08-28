package hufs.likelion.gov.domain.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hufs.likelion.gov.domain.authentication.dto.request.LoginRequest;
import hufs.likelion.gov.domain.authentication.dto.request.SignUpRequest;
import hufs.likelion.gov.domain.authentication.kakao.AuthTokens;
import hufs.likelion.gov.domain.authentication.kakao.KakaoLoginParams;
import hufs.likelion.gov.domain.authentication.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	@Autowired
	AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authService.authenticateUser(loginRequest));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
		String registered = authService.register(signUpRequest);
		return ResponseEntity.ok(registered);
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody String refreshTokenRequest) {
		String refreshToken = authService.refreshToken(refreshTokenRequest);
		return ResponseEntity.ok(refreshToken);
	}

	@PostMapping("/kakao")
	public ResponseEntity<?> loginKakao(@RequestBody KakaoLoginParams params) {
		AuthTokens login = authService.login(params);
		return new ResponseEntity<>(login, HttpStatus.OK);
	}
}
