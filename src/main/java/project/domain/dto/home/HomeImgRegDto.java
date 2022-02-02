package project.domain.dto.home;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.entity.HomeImageEntity;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HomeImgRegDto {

	private MultipartFile mainImg;
	private MultipartFile sub1Img;
	private MultipartFile sub2Img;
	private MultipartFile sub3Img;
	private MultipartFile sub4Img;
	
}
