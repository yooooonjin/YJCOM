package project.service.log;

import org.springframework.stereotype.Service;

import project.domain.dto.JoinDto;
import project.domain.entity.MemberEntityRepository;

@Service
public class LogServiceImpl implements LogService {

	MemberEntityRepository memberRepository;
	
	@Override
	public String join(JoinDto joinDto) {
		
		System.out.println(joinDto.getName());
		
		memberRepository.save(joinDto.toEntity());
		
		return "redirect:/";
	}

}
