package project.controller.host;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/host")
@Controller
public class HostController {
	
	@GetMapping("/home")
	public String homeUpdatePage() {
		return "host/home-update";
	}

}
