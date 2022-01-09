package project.controller.host;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/host")
@Controller
public class HostController {
	
	@GetMapping("/regist")
	public String hostPage() {
		return "host/host-regist";
	}

}
