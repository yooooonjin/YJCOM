package project.domain.dto.reserve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.entity.HomeEntity;
import project.domain.entity.HomeType;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationRequestHomeDto {
	private long hno;
	private String homeName;
	private HomeType homeType;
	private long homePrice;
	
	public ReservationRequestHomeDto (HomeEntity entity) {
		this.hno=entity.getHno();
		this.homeName=entity.getHomeName();
		this.homeType=entity.getHomeType();
		this.homePrice=entity.getHomePrice();
	}
}
