package project.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/home")
@Controller
public class HomesController {
	
	@GetMapping("/list")
	public String homesPage() {
		return "home/homes";
	}
	@GetMapping("/detail")
	public String homesDetail() {
		return "home/home-detail";
	}
	
	@GetMapping("/reg")
	public String homeReg() {
		return "home/home-reg";
	}

}
