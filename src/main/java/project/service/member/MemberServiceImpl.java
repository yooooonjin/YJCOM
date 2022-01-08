package project.service.member;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import project.security.dto.SecurityDto;

@Service
public class MemberServiceImpl implements MemberService {

	@Override
	public String userInfo(Model model, SecurityDto securityDto) {
		
		model.addAttribute("user", securityDto.getUsername());
		
		return "user/userInfo";
	}

}
