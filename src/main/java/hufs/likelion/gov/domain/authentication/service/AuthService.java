package hufs.likelion.gov.domain.authentication.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hufs.likelion.gov.domain.authentication.dto.request.ChangePasswordRequest;
import hufs.likelion.gov.domain.authentication.dto.request.LoginRequest;
import hufs.likelion.gov.domain.authentication.dto.request.SignUpManagerRequest;
import hufs.likelion.gov.domain.authentication.dto.request.SignUpRequest;
import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.authentication.entity.RefreshToken;
import hufs.likelion.gov.domain.authentication.entity.Role;
import hufs.likelion.gov.domain.authentication.exception.MemberException;
import hufs.likelion.gov.domain.authentication.jwt.CustomUserDetails;
import hufs.likelion.gov.domain.authentication.jwt.JwtAuthenticationResponse;
import hufs.likelion.gov.domain.authentication.jwt.JwtTokenProvider;
import hufs.likelion.gov.domain.authentication.kakao.AuthTokens;
import hufs.likelion.gov.domain.authentication.kakao.AuthTokensGenerator;
import hufs.likelion.gov.domain.authentication.kakao.OAuthProvider;
import hufs.likelion.gov.domain.authentication.kakao.interfaces.OAuthInfoResponse;
import hufs.likelion.gov.domain.authentication.kakao.interfaces.OAuthLoginParams;
import hufs.likelion.gov.domain.authentication.repository.MemberRepository;
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
	private RequestOAuthInfoService requestOAuthInfoService;
	private AuthTokensGenerator authTokensGenerator;

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

		if (signUpRequest.getRole() == Role.SUPERADMIN) {
			throw new MemberException("SuperAdmin cannot be registered");
		} else if (signUpRequest.getRole() == Role.MANAGER) {
			throw new MemberException("Manager cannot be registered");
		}

		Member member = new Member(
			signUpRequest.getUserId(),
			passwordEncoder.encode(signUpRequest.getPassword()),
			signUpRequest.getEmail(),
			signUpRequest.getProfilePhoto(),
			signUpRequest.getRole(),
			OAuthProvider.GENERAL
		);

		memberRepository.save(member);

		Optional<Member> byUserId = memberRepository.findByMemberId(signUpRequest.getUserId());
		if (byUserId.isPresent()) {
			return "User registered successfully";
		} else {
			throw new MemberException("User registration Failed");
		}
	}

	@Transactional
	public String register(SignUpManagerRequest signUpRequest, CustomUserDetails customUserDetails) {
		if (memberRepository.findByMemberId(customUserDetails.getUsername())
			.orElseThrow(() -> new MemberException("SuperAdmin not found"))
			.getRole() != Role.SUPERADMIN) {
			throw new MemberException("Only SuperAdmin can register Manager");
		}

		if (memberRepository.existsByMemberId(signUpRequest.getUserId())) {
			throw new MemberException("MemberId is already taken!");
		}

		Member member = new Member(
			signUpRequest.getUserId(),
			passwordEncoder.encode(signUpRequest.getPassword()),
			signUpRequest.getEmail(),
			Role.MANAGER,
			OAuthProvider.GENERAL
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

	@Transactional
	public AuthTokens login(OAuthLoginParams params) {
		OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
		Long userId = findOrCreateMember(oAuthInfoResponse);

		AuthTokens authTokens = authTokensGenerator.generate(userId);
		Optional<Member> member = memberRepository.findById(userId);

		Optional<RefreshToken> byUserId = refreshTokenRepository.findByMember_MemberId(member.get().getMemberId());
		if (byUserId.isPresent()) {
			RefreshToken refreshTokenEntity = byUserId.get();
			refreshTokenEntity.setToken(authTokens.getRefreshToken());
			refreshTokenRepository.save(refreshTokenEntity);
		} else {
			RefreshToken refreshTokenEntity = new RefreshToken();
			refreshTokenEntity.setToken(authTokens.getRefreshToken());
			refreshTokenEntity.setMember(member.get());
			refreshTokenEntity.setExpiryDate(tokenProvider.calculateRefreshTokenExpiryDate());
			refreshTokenRepository.save(refreshTokenEntity);
		}
		return authTokens;
	}

	private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
		return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
			.map(Member::getId)
			.orElseGet(() -> newMember(oAuthInfoResponse));
	}

	private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
		Member member = Member.builder()
			.email(oAuthInfoResponse.getEmail())
			.role(Role.CARETAKER)
			.oAuthProvider(oAuthInfoResponse.getOAuthProvider())
			.build();

		return memberRepository.save(member).getId();
	}

	public void changePassword(ChangePasswordRequest changePasswordRequest) {
		Member member = memberRepository.findByMemberId(changePasswordRequest.getMemberId())
			.orElseThrow(() -> new MemberException("Member not found"));

		if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), member.getPassword())) {
			throw new MemberException("Old password is incorrect");
		}

		member.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
		memberRepository.save(member);

	}
}
