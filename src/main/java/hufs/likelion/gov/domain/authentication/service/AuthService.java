package hufs.likelion.gov.domain.authentication.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import hufs.likelion.gov.domain.authentication.entity.RefreshToken;
import hufs.likelion.gov.domain.authentication.exception.MemberException;
import hufs.likelion.gov.domain.authentication.jwt.JwtAuthenticationResponse;
import hufs.likelion.gov.domain.authentication.jwt.JwtTokenProvider;
import hufs.likelion.gov.domain.authentication.repository.MemberRepository;
import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.authentication.entity.Role;
import hufs.likelion.gov.domain.authentication.entity.request.LoginRequest;
import hufs.likelion.gov.domain.authentication.entity.request.SignUpRequest;
import hufs.likelion.gov.domain.authentication.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	private AuthenticationManager authenticationManager;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider tokenProvider;
	private MemberRepository memberRepository;
	private RefreshTokenRepository refreshTokenRepository;

	@Transactional
	public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(),
				loginRequest.getPassword()
			)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String accessToken = tokenProvider.generateAccessToken(authentication.getName());
		String refreshTokenStr = tokenProvider.generateRefreshToken(authentication.getName());

		Optional<Member> member = memberRepository.findByMemberId(loginRequest.getUsername());
		if (member.isPresent()) {
			// 기존의 RefreshToken 삭제
			refreshTokenRepository.deleteByMember(member.get());

			// 새로운 RefreshToken 저장
			RefreshToken refreshToken = new RefreshToken();
			refreshToken.setToken(refreshTokenStr);
			refreshToken.setMember(member.get());
			refreshToken.setExpiryDate(tokenProvider.calculateRefreshTokenExpiryDate());
			refreshTokenRepository.save(refreshToken);

			return new JwtAuthenticationResponse(accessToken, refreshTokenStr);
		} else {
			throw new MemberException("Member Not Found");
		}
	}

	@Transactional
	public String register(SignUpRequest signUpRequest) {
		if (memberRepository.existsByMemberId(signUpRequest.getUserId())) {
			throw new MemberException("MemberId is already taken!");
		}

		try {
			Role.fromString(signUpRequest.getRole().toString());
		} catch (IllegalArgumentException e) {
			throw new MemberException("Invalid role provided: " + signUpRequest.getRole());
		}

		Member member = new Member(
			signUpRequest.getUserId(),
			passwordEncoder.encode(signUpRequest.getPassword()),
			signUpRequest.getEmail(),
			signUpRequest.getProfilePhoto(),
			signUpRequest.getRole()
		);

		memberRepository.save(member);

		Optional<Member> byUserId = memberRepository.findByMemberId(signUpRequest.getUserId());
		if (byUserId.isPresent()) {
			return "User registered successfully";
		} else {
			throw new MemberException("User registration Failed");
		}
	}

	public String refreshToken(String refreshTokenRequest) {
		Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(refreshTokenRequest);

		if (refreshTokenOpt.isPresent() && tokenProvider.validateToken(refreshTokenRequest)) {
			String userId = tokenProvider.getUserIdFromJWT(refreshTokenRequest);
			return tokenProvider.generateAccessToken(userId);
		} else {
			throw new MemberException("Invalid refresh token");
		}
	}
}
