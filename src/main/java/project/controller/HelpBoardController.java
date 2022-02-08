package project.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import project.domain.dto.helpBoard.HelpBoardKeywordDto;
import project.domain.dto.helpBoard.HelpBoardWriteDto;
import project.service.HelpBoardService;

@RequiredArgsConstructor
@RequestMapping("/help")
@Controller
public class HelpBoardController {
	
	private final HelpBoardService service;
	//도움말 페이지
	@GetMapping
	public String helpPage(Model model) {
		return service.helpList(model);
	}
	//카테고리별 리스트
	@GetMapping("/category")
	public String helpCategoryPage(Model model, String category) {
		return service.helpCategory(model,category);
	}
	//키워드 리스트
	@GetMapping("/keyword")
	public String helpKeywordPage(Model model, HelpBoardKeywordDto keywordDto) {
		return service.helpKeyword(model,keywordDto);
	}
	//도움말 쓰기 페이지
	@GetMapping("/write")
	public String helpWritePage() {
		return "help/help-write";
	}
	//도움말 쓰기
	@PostMapping("/write")
	public String helpWrite(Principal principal, HelpBoardWriteDto writeDto) {
		return service.helpWrite(principal, writeDto);
	}
	
}
