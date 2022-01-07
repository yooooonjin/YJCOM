package project.service.log;

import org.springframework.stereotype.Service;

import project.domain.dto.JoinDto;
import project.domain.entity.MemberEntity;
import project.domain.entity.MemberEntityRepository;
import project.domain.entity.MemberRole;

@Service
public class LogServiceImpl implements LogService {

	MemberEntityRepository memberRepository;
	
	@Override
	public String join(JoinDto joinDto) {
		
		System.out.println(joinDto.getName());
		
		MemberEntity entity= joinDto.toEntity();
		entity.addRole(MemberRole.USER);
		
		memberRepository.save(entity);
		
		return "redirect:/";
	}

}
