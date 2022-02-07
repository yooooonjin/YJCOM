package project.service;

import org.springframework.ui.Model;

import project.security.dto.SecurityDto;

public interface HostService {

	String hostPage(Model model, SecurityDto securityDto, int page);

	String reservationPage(Model model, SecurityDto securityDto);

	void homeDelete(long hno);

}
