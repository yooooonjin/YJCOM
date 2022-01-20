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
import lombok.extern.log4j.Log4j2;
import project.domain.dto.home.HomeListDto;
import project.domain.dto.home.HomeSearchDto;
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

@Log4j2
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
		
		return "redirect:/home/list";
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
	
	//조건에 맞는 집 불러오기
	@Override
	public String homesearch(Model model, int page, HomeSearchDto searchDto) {
		
		String result= searchDto.getLocation();
		
		String[] locationArr= searchDto.getLocation().split("[ ]");
		log.info("자치구={}",locationArr[1]);
		String location=locationArr[1];
		
		//문자열로 넘어온 게스트 수를 정수로 변환
		String guestsStr=searchDto.getGuestsStr();
		String guestStr_=guestsStr.replaceAll("[^0-9]", "");
		if(guestStr_==null || guestStr_.equals(""))guestStr_="0";
		int guests = Integer.parseInt(guestStr_);

		//해당 지역 / 최소인원 조건에 맞는 집
		List<Long> hno = homeRepository.selectHome(location,guests);
		
		//TODO DTO에 담아서 전달
		List<HomeEntity> homeEntity= homeRepository.findAllById(hno);
		model.addAttribute("homes", homeEntity);
		model.addAttribute("searchDto", searchDto);
		return "home/homes";
		
	}

}
