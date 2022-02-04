package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import project.domain.dto.JoinDto;
import project.service.LogService;

@RequiredArgsConstructor
@Controller
public class LogController {
	
 	private final LogService service;
	
 	@GetMapping("/login")
 	public String loginPage() {
 		return "log/login";
 	}
 	
 	@GetMapping("/join")
 	public String joinPage() {
 		return "log/join";
 	}
 	
 	@PostMapping("/join")
 	public String join(JoinDto joinDto) {
 		return service.join(joinDto);
 	}
}
