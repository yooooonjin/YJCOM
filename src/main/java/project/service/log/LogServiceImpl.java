package project.service.log;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.domain.dto.JoinDto;
import project.domain.entity.MemberEntity;
import project.domain.entity.MemberEntityRepository;
@RequiredArgsConstructor
@Service
public class LogServiceImpl implements LogService {

	final MemberEntityRepository memberRepository;
	
	@Override
	public String join(JoinDto joinDto) {
		
		System.out.println(joinDto.getName());
		
		MemberEntity entity= joinDto.toEntity();
		
		memberRepository.save(entity);
		
		return "redirect:/";
	}

}
