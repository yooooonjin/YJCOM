package project.domain.dto.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.entity.HomeOption;
import project.domain.entity.HomeType;
import project.domain.entity.MemberEntity;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HomeRegDto {
	
	private String homeName;
	private String homeAddress;
	private long homePrice;
	
	private int maximumNumber;
	private int bedNumber;
	private int bedroomNumber;
	private int bathroomNumber;
	
	private HomeType homeType;
	
	private HomeOption[] homeOption;
	
	private String homeIntro;
	
	private String homePhoto;
	
	private MemberEntity member;
	

}
