package project.controller.home;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import project.domain.dto.home.homeRegDto;
import project.security.dto.SecurityDto;
import project.service.home.HomeService;
import project.service.impl.HomeServiceImp;

@Log4j2
@RequestMapping("/home")
@RequiredArgsConstructor
@Controller
public class HomesController {
	
	final HomeService homeService;
	
	
	//집 리스트
	@GetMapping("/list")
	public String homesPage(Model model) {
		return homeService.homeList(model);
	}
	//집 상세페이지
	@GetMapping("/detail")
	public String homesDetail(Model medel) {
		return homeService.homeDetail(medel);
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
