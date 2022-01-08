package project.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/homes")
@Controller
public class HomesController {
	
	@GetMapping
	public String homesPage() {
		return "homes/homes";
	}
	@GetMapping("/detail")
	public String homesDetail() {
		return "homes/homes_detail";
	}

}
