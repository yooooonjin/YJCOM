package project.controller.log;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import project.domain.dto.JoinDto;
import project.service.log.LogService;

@RequiredArgsConstructor
@Controller
public class LogController {
	
 	private final LogService service;
	
 	@GetMapping("/page/login")
 	public String loginPage() {
 		return "page/login";
 	}
 	
 	@GetMapping("/page/join")
 	public String joinPage() {
 		return "page/join";
 	}
 	
 	@PostMapping("/page/join")
 	public String join(JoinDto joinDto) {
 		return service.join(joinDto);
 	}
}
