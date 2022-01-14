package project.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import project.domain.dto.MemberUpdateDto;
import project.domain.dto.home.HomeListDto;
import project.domain.entity.HomeEntity;
import project.domain.entity.HomeEntityRepository;
import project.domain.entity.MemberEntity;
import project.domain.entity.MemberEntityRepository;
import project.security.dto.SecurityDto;
import project.service.member.MemberService;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	private final MemberEntityRepository memberRepository;
	private final HomeEntityRepository homeRepository;
	
	@Override
	public String memberInfo(Model model, SecurityDto securityDto) {
		
		MemberEntity entity= memberRepository.findById(securityDto.getUsername()).get();
		model.addAttribute("member",entity);
		
		List<HomeEntity> homeEntity = homeRepository.findAllByMember_email(entity.getEmail());
		List<HomeListDto> hostHome= homeEntity.stream().map(HomeListDto::new).collect(Collectors.toList());
		
		model.addAttribute("hostHome",hostHome);
		
		return "member/member-info";
	}
	
	@Transactional
	@Override
	public void memberUpdate(MemberUpdateDto updateDto ,Model model) {
		
		MemberEntity result = memberRepository.findById(updateDto.getEmail()).map(entity->entity.updateNameAndPhonenumberAndAddress(updateDto)).get();
				
	}

}
