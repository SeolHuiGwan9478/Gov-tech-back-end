package hufs.likelion.gov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import hufs.likelion.gov.entity.request.LoginRequest;
import hufs.likelion.gov.entity.request.SignUpRequest;
import hufs.likelion.gov.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	@Autowired
	AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			return ResponseEntity.ok(authService.authenticateUser(loginRequest));
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
		try {
			String registered = authService.register(signUpRequest);
			return ResponseEntity.ok(registered);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody String refreshTokenRequest) {
		try {
			String refreshToken = authService.refreshToken(refreshTokenRequest);
			return ResponseEntity.ok(refreshToken);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
