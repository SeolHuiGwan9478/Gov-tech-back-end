package hufs.likelion.gov.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hufs.likelion.gov.entity.Member;
import hufs.likelion.gov.jwt.CustomUserDetails;
import hufs.likelion.gov.repository.MemberRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByMemberId(username).orElse(null);
		if (member != null) {
			return new CustomUserDetails(member);
		}
		throw new UsernameNotFoundException("User not found with username: " + username);
	}
}
