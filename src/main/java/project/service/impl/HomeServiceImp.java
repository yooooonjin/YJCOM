package project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import project.domain.dto.home.HomeListDto;
import project.domain.dto.home.homeRegDto;
import project.domain.entity.HomeEntity;
import project.domain.entity.HomeEntityRepository;
import project.domain.entity.HomeOption;
import project.domain.entity.HomeReviewEntity;
import project.domain.entity.HomeReviewEntityRepository;
import project.domain.entity.MemberEntityRepository;
import project.domain.entity.MemberRole;
import project.security.dto.SecurityDto;
import project.service.home.HomeService;
import project.util.PageInfo;

@RequiredArgsConstructor
@Service
public class HomeServiceImp implements HomeService {
	
	final HomeEntityRepository homeRepository;
	final MemberEntityRepository memberRepository;
	final HomeReviewEntityRepository reviewRepository;
	
	//집 등록
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
		
		return "/home/detail/"+entity.getHno();
	}
	//집 전체 불러오기 //페이징처리
	@Override
	public String homeList(Model model,int page) {
		
		//한 페이지당 집 3개씩
		Pageable pageable =PageRequest.of(page-1, 3, Direction.DESC, "hno");
		Page<HomeEntity> homesEntity =homeRepository.findAll(pageable);
		List<HomeListDto> homes =homesEntity.stream().map(HomeListDto::new).collect(Collectors.toList());
		model.addAttribute("homes",homes);
		
		//페이지 3개씩
		PageInfo paging=new PageInfo(homesEntity.getTotalPages(),page,3);
		model.addAttribute("paging",paging);
		return "home/homes";
		
	}
	//집 디테일 페이지
	@Override
	public String homeDetail(Model model, long hno) {
		
		HomeListDto homeDetail= homeRepository.findById(hno).map(HomeListDto::new).orElseThrow();
		model.addAttribute("homeDetail", homeDetail);
		
		//후기//TODO dto에 담기
		List<HomeReviewEntity> homeReview = reviewRepository.findAllByHome_hno(hno);
		model.addAttribute("homeReview", homeReview);
		return "home/home-detail";
	}

}
