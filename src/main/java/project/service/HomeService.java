package project.service;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import project.domain.dto.home.HomeSearchDto;
import project.domain.dto.home.HomeImgRegDto;
import project.domain.dto.home.HomeRegDto;
import project.security.dto.SecurityDto;

public interface HomeService {

	String homeReg(HomeRegDto regDto, SecurityDto securityDto, HomeImgRegDto imgRegDto);

	String homeList(Model model, int page);

	String homeDetail(Model model, long hno);

	String homesearch(Model model, int page, HomeSearchDto searchDto);

	String tempImgUpload(MultipartFile fileImg, String imageName);
	
}
