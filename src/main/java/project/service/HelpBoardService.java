package project.service;

import java.security.Principal;

import org.springframework.ui.Model;

import project.domain.dto.helpBoard.HelpBoardWriteDto;

public interface HelpBoardService {

	String helpWrite(Principal principal, HelpBoardWriteDto writeDto);

	String helpList(Model model);

	String helpCategory(Model model, String category);

	String helpKeyword(Model model, String condition, String keyword);

}
