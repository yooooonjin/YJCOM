package project.service.impl;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.domain.dto.JoinDto;
import project.domain.entity.MemberEntity;
import project.domain.entity.MemberEntityRepository;
import project.domain.entity.MemberRole;
import project.service.LogService;
@RequiredArgsConstructor
@Service
public class LogServiceImpl implements LogService {

	final MemberEntityRepository memberRepository;
	final PasswordEncoder passwordEncoder;
	
	@Transactional
	@Override
	public String join(JoinDto joinDto) {
		
		
		MemberEntity entity= MemberEntity.builder()
				.email(joinDto.getEmail()).name(joinDto.getName()).password(passwordEncoder.encode(joinDto.getPassword())).gender(joinDto.getGender())
				.phoneNumber(joinDto.getPhoneNumber()).birthday(joinDto.getBirthday()).address(joinDto.getAddress()).photoName("default_photo.jpg")
				.build();
		
		entity.addRole(MemberRole.USER);
		
		memberRepository.save(entity);
		
		return "redirect:/";
	}

}
