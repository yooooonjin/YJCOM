package project.service.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.domain.dto.log.JoinDto;
import project.domain.entity.MemberEntity;
import project.domain.entity.MemberEntityRepository;
import project.domain.entity.MemberRole;
import project.service.LogService;
@RequiredArgsConstructor
@Service
public class LogServiceImpl implements LogService {

	final MemberEntityRepository memberRepository;
	final PasswordEncoder passwordEncoder;
	final HttpServletRequest request;
	
	//회원가입
	@Transactional
	@Override
	public String join(JoinDto joinDto) {
		//ip등록
		joinDto.setUserIp(request.getRemoteAddr());
		
		MemberEntity entity= MemberEntity.builder()
				.email(joinDto.getEmail()).name(joinDto.getName()).password(passwordEncoder.encode(joinDto.getPassword())).gender(joinDto.getGender())
				.phoneNumber(joinDto.getPhoneNumber()).birthday(joinDto.getBirthday()).address(joinDto.getAddress()).photoName("default_photo.jpg")
				.userIp(joinDto.getUserIp())
				.build();
		//USER 권한
		entity.addRole(MemberRole.USER);
		
		memberRepository.save(entity);
		
		return "redirect:/";
	}
	//아이디 중복체크
	@Override
	public String emailCheck(String email) {
		
		String result="*존재하는 아이디입니다.";
		Optional<MemberEntity> member= memberRepository.findById(email);
		if(member.isEmpty()) {
			return null;
		}
		return result;
	}

}
