package project.service;

import project.domain.dto.log.JoinDto;

public interface LogService {

	String join(JoinDto joinDto);

	String emailCheck(String email);

}
