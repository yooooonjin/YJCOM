package project.service;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import project.domain.dto.home.HomeReserveDto;
import project.domain.dto.member.MemberUpdateDto;
import project.security.dto.SecurityDto;

public interface MemberService {

	String memberInfo(Model model, SecurityDto securityDto, int page);

	void memberUpdate(MemberUpdateDto updateDto, Model model);

	String homeReserve(long hno, SecurityDto securityDto, HomeReserveDto reserveDto, String message);


	void reviewWrite(long resNo, long hno, SecurityDto securityDto, String review);

	String homeReserveRequest(long hno, HomeReserveDto reserveDto, Model model);

	String personalInfoPage(SecurityDto securityDto, Model model);

	String photoUpload(MultipartFile fileImg, SecurityDto securityDto);

	String memberPhotoPage(Model model, SecurityDto securityDto);

	void memberDelete(SecurityDto securityDto);


}
