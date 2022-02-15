package project.domain.dto.home;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.entity.HomeEntity;
import project.domain.entity.HomeReviewEntity;
import project.domain.entity.MemberEntity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HomeReviewDto {
	
	private long rno;
	private String review;
	private String name;
	private String photoName;
	private HomeEntity home;
	private LocalDateTime createdDate;
	private LocalDateTime updateDate;
	
	public HomeReviewDto(HomeReviewEntity entity) {
		this.review = entity.getReview();
		this.name=entity.getMember().getName();
		this.photoName=entity.getMember().getPhotoName();
		this.home = entity.getHome();
		this.createdDate = entity.getCreatedDate();
		this.updateDate = entity.getUpdatedDate();
	}
	
	
	
	

}
