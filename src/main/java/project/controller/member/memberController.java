package project.controller.member;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import project.domain.dto.MemberUpdateDto;
import project.domain.dto.home.HomeReserveDto;
import project.security.dto.SecurityDto;
import project.service.member.MemberService;

@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class memberController {
	
	private final MemberService service;
	
	//프로필 페이지 이동
	@GetMapping("/info")
	public String memberInfo(Model model, @AuthenticationPrincipal SecurityDto securityDto) {
		return service.memberInfo(model,securityDto);
	}
	//회원정보수정
	@ResponseBody
	@PutMapping
	public void memberUpdate(MemberUpdateDto updateDto,Model model) {
		service.memberUpdate(updateDto,model);
	}
	//TODO 예약테이블이 없어 리뷰 서비스 진행 불가
	@GetMapping("/home/review")
	public String reviewWritePage() {
		return "/member/home-review";
	}
	//숙소예약
	@PostMapping("/home/reserve/{hno}")
	public String homeReserve(@PathVariable long hno, @AuthenticationPrincipal SecurityDto securityDto, HomeReserveDto reserveDto) {
		return service.homeReserve(hno,securityDto,reserveDto);
	}

}
