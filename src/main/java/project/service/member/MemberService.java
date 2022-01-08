package project.service.member;

import org.springframework.ui.Model;

import project.security.dto.SecurityDto;

public interface MemberService {


	String userInfo(Model model, SecurityDto securityDto);

}
