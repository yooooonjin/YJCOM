package project.service.log;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.domain.dto.JoinDto;
import project.domain.entity.MemberEntityRepository;
@RequiredArgsConstructor
@Service
public class LogServiceImpl implements LogService {

	final MemberEntityRepository memberRepository;
	
	@Override
	public String join(JoinDto joinDto) {
		
		memberRepository.save(joinDto.toEntity());
		
		return "/log/login";
	}

}
