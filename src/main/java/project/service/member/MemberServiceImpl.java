package project.service.member;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import project.domain.dto.MemberUpdateDto;
import project.domain.entity.MemberEntity;
import project.domain.entity.MemberEntityRepository;
import project.security.dto.SecurityDto;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	private final MemberEntityRepository memberRepository;
	
	@Override
	public String memberInfo(Model model, SecurityDto securityDto) {
		
		MemberEntity entity= memberRepository.findById(securityDto.getUsername()).get();
		model.addAttribute("member",entity);
		
		return "member/member-info";
	}
	
	@Transactional
	@Override
	public void memberUpdate(MemberUpdateDto updateDto) {
		System.out.println(updateDto.getName());
		
		memberRepository.findById(updateDto.getEmail()).map(entity->entity.updateNameAndPhonenumberAndAddress(updateDto));
		
	}

}
