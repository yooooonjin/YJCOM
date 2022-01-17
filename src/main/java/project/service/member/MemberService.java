package project.service.member;

import org.springframework.ui.Model;

import project.domain.dto.MemberUpdateDto;
import project.domain.dto.home.HomeReserveDto;
import project.domain.dto.home.HomeReviewDto;
import project.security.dto.SecurityDto;

public interface MemberService {

	String memberInfo(Model model, SecurityDto securityDto);

	void memberUpdate(MemberUpdateDto updateDto, Model model);

	String homeReserve(long hno, SecurityDto securityDto, HomeReserveDto reserveDto);


	void reviewWrite(long hno, SecurityDto securityDto, String review);


}
