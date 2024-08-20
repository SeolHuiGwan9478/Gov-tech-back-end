package hufs.likelion.gov.jwt;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import hufs.likelion.gov.entity.Member;

public class CustomUserDetails implements UserDetails {

	private final String username;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;

	public CustomUserDetails(Member member) {
		this.username = member.getMemberId();
		this.password = member.getPassword();
		this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")); // 권한 설정 필요 시 추가
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
