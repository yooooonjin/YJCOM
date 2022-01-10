package project.controller.member;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import project.domain.dto.MemberUpdateDto;
import project.security.dto.SecurityDto;
import project.service.member.MemberService;

@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class memberController {
	
	private final MemberService service;
	
	@GetMapping("/info")
	public String memberInfo(Model model, @AuthenticationPrincipal SecurityDto securityDto) {
		return service.memberInfo(model,securityDto);
	}
	
	@ResponseBody
	@PutMapping
	public void memberUpdate(MemberUpdateDto updateDto,Model model) {
		System.out.println("name : "+updateDto.getName());
		service.memberUpdate(updateDto,model);
	}

}
