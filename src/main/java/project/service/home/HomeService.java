package project.service.home;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import project.domain.dto.home.HomeSearchDto;
import project.domain.dto.home.homeRegDto;
import project.security.dto.SecurityDto;

public interface HomeService {

	String homeReg(homeRegDto regDto, SecurityDto securityDto);

	String homeList(Model model, int page);

	String homeDetail(Model model, long hno);

	String homesearch(Model model, int page, HomeSearchDto searchDto);

	String tempMainImgUpload(MultipartFile file);
	
}
