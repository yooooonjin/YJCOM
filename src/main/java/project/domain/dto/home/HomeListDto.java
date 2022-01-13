package project.domain.dto.home;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.entity.HomeEntity;
import project.domain.entity.HomeOption;
import project.domain.entity.HomeType;
import project.domain.entity.MemberEntity;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HomeListDto {
	
	private String homeName;
	private String homeAddress;
	private long homePrice;
	
	private int maximumNumber;
	private int bedNumber;
	private int bedroomNumber;
	private int bathroomNumber;
	
	private HomeType homeType;
	
	private Set<HomeOption> homeOptionSet;
	
	private String homeIntro;
	
	private String homePhoto;
	
	private MemberEntity member;

	public HomeListDto(HomeEntity entity) {
		this.homeName = entity.getHomeName();
		this.homeAddress = entity.getHomeAddress();
		this.homePrice = entity.getHomePrice();
		this.maximumNumber = entity.getMaximumNumber();
		this.bedNumber = entity.getBedNumber();
		this.bedroomNumber = entity.getBedroomNumber();
		this.bathroomNumber = entity.getBathroomNumber();
		this.homeType = entity.getHomeType();
		this.homeOptionSet=entity.getHomeOptionSet();
		this.homeIntro = entity.getHomeIntro();
		this.homePhoto = entity.getHomePhoto();
		//this.member = member;
	}
	
	
	
	
	

}
