package project.controller.log;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.security.dto.SecurityDto;
import project.service.member.MemberService;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class memberController {
	
	private final MemberService service;
	
	@GetMapping("/show")
	public String userInfo(Model model, @AuthenticationPrincipal SecurityDto securityDto) {
		return service.userInfo(model,securityDto);
	}

}
