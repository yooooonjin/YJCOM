package project.controller.home;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import project.domain.dto.home.HomeSearchDto;
import project.domain.dto.home.homeRegDto;
import project.security.dto.SecurityDto;
import project.service.home.HomeService;

@Log4j2
@RequestMapping("/home")
@RequiredArgsConstructor
@Controller
public class HomesController {
	
	final HomeService homeService;
	
	
	//집 리스트
	@GetMapping("/list")
	public String homesPage(Model model,@RequestParam(defaultValue = "1") int page) {
		return homeService.homeList(model,page);
	}
	//조건에 맞는 집 리스트
	@PostMapping("/search/list")
	public String homesSearch(Model model,@RequestParam(defaultValue = "1") int page, HomeSearchDto searchDto) {
		return homeService.homesearch(model,page,searchDto);
	}
	//집 상세페이지
	@GetMapping("/detail/{hno}")
	public String homesDetail(Model medel, @PathVariable long hno) {
		return homeService.homeDetail(medel,hno);
		//return "home/home-detail";
	}
	//숙소 등록 페이지
	@GetMapping("/reg")
	public String homeRegPage() {
		return "home/home-reg";
	}
	//숙소 등록
	@PostMapping("/reg")
	public String homeReg(homeRegDto regDto, @AuthenticationPrincipal SecurityDto securityDto) {
		
		log.info("memberId={}",securityDto.getUsername());
		
		return homeService.homeReg(regDto,securityDto);
	}
	
}
