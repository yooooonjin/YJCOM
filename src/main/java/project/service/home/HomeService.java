package project.service.home;

import org.springframework.ui.Model;

import project.domain.dto.home.homeRegDto;
import project.security.dto.SecurityDto;

public interface HomeService {

	String homeReg(homeRegDto regDto, SecurityDto securityDto);

	String homeList(Model model);

	String homeDetail(Model medel);
	
}
