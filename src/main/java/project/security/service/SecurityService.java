package project.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.RequiredArgsConstructor;
import project.domain.entity.MemberEntity;
import project.domain.entity.MemberEntityRepository;
import project.security.dto.SecurityDto;

@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {
	
	final MemberEntityRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		MemberEntity memberEntity = memberRepository.findById(username).get();
		
		if(memberEntity==null) throw new UsernameNotFoundException("회원조회실패");
		
		return new SecurityDto(memberEntity);
	}

}
