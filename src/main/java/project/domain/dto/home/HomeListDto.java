package project.domain.dto.home;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.entity.HomeEntity;
import project.domain.entity.HomeOption;
import project.domain.entity.HomeType;
import project.domain.entity.MemberEntity;
import project.domain.entity.ReservationEntity;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HomeListDto {
	
	private long hno;
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
	
	private List<ReservationEntity> reservations;
	private MemberEntity member;

	
	
	public HomeListDto(HomeEntity entity) {
	
			this.hno=entity.getHno();
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
			this.member = entity.getMember();
	
	}
	
	
	
	
	

}
