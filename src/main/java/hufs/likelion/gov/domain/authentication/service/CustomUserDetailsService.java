package hufs.likelion.gov.domain.authentication.service;

import static hufs.likelion.gov.global.constant.GlobalConstant.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.authentication.exception.MemberException;
import hufs.likelion.gov.domain.authentication.jwt.CustomUserDetails;
import hufs.likelion.gov.domain.authentication.repository.MemberRepository;
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
		throw new MemberException(NOT_FOUND_MEMBER_ERR_MSG);
	}
}
