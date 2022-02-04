package project.service;

import org.springframework.ui.Model;

import project.security.dto.SecurityDto;

public interface MessageService {

	String messagePage(SecurityDto securityDto, Model model);

	String messageDetail(SecurityDto securityDto, Model model, String targetEmail);

	void messageDetailWrite(SecurityDto securityDto, Model model, String targetId, String message);

}
