package project.controller.home;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import project.domain.dto.home.HomeSearchDto;
import project.domain.dto.home.HomeImgRegDto;
import project.domain.dto.home.HomeRegDto;
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
	//숙소 서브 이미지 temp 폴더에 임시 보관
	@ResponseBody
	@PostMapping("/reg/tempImgUpload")
	public String tempSubImgUpload(MultipartFile fileImg, String imageName) {
		System.out.println("name"+imageName);
		return homeService.tempImgUpload(fileImg,imageName);
	}
	//숙소 등록
	@PostMapping("/reg")
	public String homeReg(HomeRegDto regDto, @AuthenticationPrincipal SecurityDto securityDto,HomeImgRegDto imgRegDto) {
		return homeService.homeReg(regDto,securityDto,imgRegDto);
	}
	
}
