package project.service.member;

import org.springframework.ui.Model;

import project.domain.dto.MemberUpdateDto;
import project.security.dto.SecurityDto;

public interface MemberService {

	String memberInfo(Model model, SecurityDto securityDto);

	void memberUpdate(MemberUpdateDto updateDto, Model model);


}
