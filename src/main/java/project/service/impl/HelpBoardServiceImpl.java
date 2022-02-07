package project.service.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import project.domain.dto.helpBoard.HelpBoardListDto;
import project.domain.dto.helpBoard.HelpBoardWriteDto;
import project.mybatis.mapper.BoardMapper;
import project.service.HelpBoardService;

@RequiredArgsConstructor
@Service
public class HelpBoardServiceImpl implements HelpBoardService {

	private final BoardMapper boardMapper;
	
	//도움말 글쓰기
	@Override
	public String helpWrite(Principal principal, HelpBoardWriteDto writeDto) {
		writeDto.setEmail(principal.getName());
		boardMapper.boadSave(writeDto);
		return "redirect:/help";
	}
	//도움말 전체 리스트
	@Override
	public String helpList(Model model) {
		List<HelpBoardListDto> result=boardMapper.boardList();
		model.addAttribute("boardList", result);
		return "help/help-list";
	}
	//카페고리별 리스트
	@Override
	public String helpCategory(Model model, String category) {
		List<HelpBoardListDto> result=boardMapper.boardCategoryList(category);
		model.addAttribute("boardList", result);
		return "help/help-detail";
	}
	@Override
	public String helpKeyword(Model model, String condition, String keyword) {
		
		//제목중 keyword 포함 리스트
		if(condition.equals("subject")) {
			List<HelpBoardListDto> result=boardMapper.boardSubjectKeywordList(keyword);
			model.addAttribute("boardList", result);
		}
		//제목중 keyword 포함 리스트
		if(condition.equals("email")) {
			List<HelpBoardListDto> result=boardMapper.boardEmailKeywordList(keyword);
			model.addAttribute("boardList", result);
		}
		return "help/help-detail";
	}

}
