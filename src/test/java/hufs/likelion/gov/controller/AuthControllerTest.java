package hufs.likelion.gov.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.webjars.NotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;

import hufs.likelion.gov.domain.authentication.controller.AuthController;
import hufs.likelion.gov.domain.authentication.entity.Role;
import hufs.likelion.gov.domain.authentication.entity.request.LoginRequest;
import hufs.likelion.gov.domain.authentication.entity.request.SignUpRequest;
import hufs.likelion.gov.domain.authentication.jwt.JwtAuthenticationResponse;
import hufs.likelion.gov.domain.authentication.jwt.JwtTokenProvider;
import hufs.likelion.gov.domain.authentication.service.AuthService;
import hufs.likelion.gov.domain.authentication.service.CustomUserDetailsService;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthService authService;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@MockBean
	private CustomUserDetailsService customUserDetailsService;

	@InjectMocks
	private AuthController authController;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
	}

	@Test
	public void testAuthenticateUserSuccess() throws Exception {
		LoginRequest loginRequest = new LoginRequest("testUser", "testPassword");
		JwtAuthenticationResponse response = new JwtAuthenticationResponse("accessToken", "refreshToken");

		when(authService.authenticateUser(any(LoginRequest.class))).thenReturn(response);

		mockMvc.perform(post("/api/auth/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(response)));
	}

	@Test
	public void testAuthenticateUserNotFound() throws Exception {
		LoginRequest loginRequest = new LoginRequest("testUser", "testPassword");

		when(authService.authenticateUser(any(LoginRequest.class))).thenThrow(
			new NotFoundException("Member Not Found"));

		mockMvc.perform(post("/api/auth/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))
			.andExpect(status().isNotFound())
			.andExpect(content().string("Member Not Found"));
	}

	@Test
	public void testSignupSuccess() throws Exception {
		SignUpRequest signUpRequest = new SignUpRequest("testUser", "testPassword", "testEmail@example.com", "USER",
			Role.Manager);

		when(authService.register(any(SignUpRequest.class))).thenReturn("User registered successfully");

		mockMvc.perform(post("/api/auth/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpRequest)))
			.andExpect(status().isOk())
			.andExpect(content().string("User registered successfully"));
	}

	@Test
	public void testSignupMemberIdTaken() throws Exception {
		SignUpRequest signUpRequest = new SignUpRequest("testUser", "testPassword", "testEmail@example.com", "USER",
			null);

		when(authService.register(any(SignUpRequest.class))).thenThrow(
			new RuntimeException("MemberId is already taken!"));

		mockMvc.perform(post("/api/auth/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpRequest)))
			.andExpect(status().isBadRequest())
			.andExpect(content().string("MemberId is already taken!"));
	}

	@Test
	public void testRefreshTokenSuccess() throws Exception {
		String refreshTokenRequest = "validRefreshToken";
		String newAccessToken = "newAccessToken";

		when(authService.refreshToken(any(String.class))).thenReturn(newAccessToken);

		mockMvc.perform(post("/api/auth/refresh")
				.contentType(MediaType.APPLICATION_JSON)
				.content(refreshTokenRequest))
			.andExpect(status().isOk())
			.andExpect(content().string(newAccessToken));
	}

	@Test
	public void testRefreshTokenInvalid() throws Exception {
		String refreshTokenRequest = "invalidRefreshToken";

		when(authService.refreshToken(any(String.class))).thenThrow(new RuntimeException("Invalid refresh token"));

		mockMvc.perform(post("/api/auth/refresh")
				.contentType(MediaType.APPLICATION_JSON)
				.content(refreshTokenRequest))
			.andExpect(status().isBadRequest())
			.andExpect(content().string("Invalid refresh token"));
	}
}