package hufs.likelion.gov.domain.authentication.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.accessTokenExpiration}")
	private long accessTokenExpirationInMs;

	@Value("${jwt.refreshTokenExpiration}")
	private long refreshTokenExpirationInMs;

	// Access Token 생성
	public String generateAccessToken(String username) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + accessTokenExpirationInMs);

		return Jwts.builder()
			.setSubject(username)
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(SignatureAlgorithm.HS512, jwtSecret)
			.compact();
	}

	// Refresh Token 생성
	public String generateRefreshToken(String username) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + refreshTokenExpirationInMs);

		return Jwts.builder()
			.setSubject(username)
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(SignatureAlgorithm.HS512, jwtSecret)
			.compact();
	}

	public String getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(jwtSecret)
			.parseClaimsJws(token)
			.getBody();

		return claims.getSubject();
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public Date calculateRefreshTokenExpiryDate() {
		return new Date(System.currentTimeMillis() + refreshTokenExpirationInMs);
	}

}

