package project.domain.dto.host;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.entity.HomeEntity;
import project.domain.entity.ReservationEntity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HostReservationDto {
	
	private HomeEntity home;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private int guests;
	private long totalPrice;
	
	public HostReservationDto (ReservationEntity entity) {
		this.home=entity.getHome();
		this.checkIn=entity.getCheckIn();
		this.checkOut=entity.getCheckOut();
		this.guests=entity.getGuests();
		this.totalPrice=entity.getTotalPrice();
	}

}
