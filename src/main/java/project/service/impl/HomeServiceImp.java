package project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import project.domain.dto.home.HomeListDto;
import project.domain.dto.home.homeRegDto;
import project.domain.entity.HomeEntity;
import project.domain.entity.HomeEntityRepository;
import project.domain.entity.HomeOption;
import project.domain.entity.MemberEntityRepository;
import project.domain.entity.MemberRole;
import project.security.dto.SecurityDto;
import project.service.home.HomeService;

@RequiredArgsConstructor
@Service
public class HomeServiceImp implements HomeService {
	
	final HomeEntityRepository homeRepository;
	final MemberEntityRepository memberRepository;
	
	@Transactional
	@Override
	public String homeReg(homeRegDto regDto, SecurityDto securityDto) {
		HomeEntity entity= HomeEntity.builder().homeName(regDto.getHomeName()).homeAddress(regDto.getHomeAddress())
			.homePrice(regDto.getHomePrice()).maximumNumber(regDto.getMaximumNumber()).bedNumber(regDto.getBedNumber())
			.bedroomNumber(regDto.getBedroomNumber()).bathroomNumber(regDto.getBathroomNumber())
			.homeType(regDto.getHomeType())
			//.homeOptionSet(null)
			.homeIntro(regDto.getHomeIntro())
			.homePhoto(regDto.getHomePhoto()).useable("y")
			.member(memberRepository.findById(securityDto.getUsername()).get())
			.build();
		
		HomeOption[] homeOption = regDto.getHomeOption();
		
		for(int i=0;i<homeOption.length;i++) {
			entity.addHomeOption(homeOption[i]);
		}
		
		homeRepository.save(entity);
		
		memberRepository.findById(securityDto.getUsername()).get().addRole(MemberRole.HOST);
		
		return "redirect:/member/info";
	}

	@Override
	public String homeList(Model model) {
		List<HomeListDto> homes= homeRepository.findAll().stream().map(HomeListDto::new).collect(Collectors.toList());
		
		model.addAttribute("homes",homes);
		return "home/homes";
	}

	@Override
	public String homeDetail(Model model, long hno) {
		
		HomeListDto homeDetail= homeRepository.findById(hno).map(HomeListDto::new).orElseThrow();
		model.addAttribute("homeDetail", homeDetail);
		return "home/home-detail";
	}

}
