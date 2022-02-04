package project.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import project.security.dto.SecurityDto;
import project.service.HostService;

@RequiredArgsConstructor
@RequestMapping("/host")
@Controller
public class HostController {
	private final HostService service;
	
	//호스트 페이지 이동
	@GetMapping
	public String hostPage(Model model, @AuthenticationPrincipal SecurityDto securityDto,@RequestParam(defaultValue = "1") int page) {
		return service.hostPage(model,securityDto,page);
	}
	
	//숙소 삭제
	@ResponseBody
	@DeleteMapping
	public void homeDelete(long hno) {
		service.homeDelete(hno);
	}
	
	//예약 페이지 이동
	@GetMapping("/reservation")
	public String reservationPage(Model model, @AuthenticationPrincipal SecurityDto securityDto,@RequestParam(defaultValue = "1") int page) {
		return service.reservationPage(model,securityDto,page);
	}

}
