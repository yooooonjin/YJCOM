package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import project.domain.dto.log.JoinDto;
import project.service.LogService;

@RequiredArgsConstructor
@Controller
public class LogController {
	
 	private final LogService service;
	
 	@GetMapping("/signin")
 	public String loginPage() {
 		return "log/signin";
 	}
 	
 	@GetMapping("/join")
 	public String joinPage() {
 		return "log/join";
 	}
 	@ResponseBody
 	@PostMapping("/join/check")
 	public String emailCheck(String email) {
 		return service.emailCheck(email);
 	}
 	
 	@PostMapping("/join")
 	public String join(JoinDto joinDto) {
 		return service.join(joinDto);
 	}
}
