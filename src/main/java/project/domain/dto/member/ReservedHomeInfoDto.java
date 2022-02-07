package project.domain.dto.member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.entity.HomeType;
import project.domain.entity.ReservationEntity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservedHomeInfoDto {
	
	//예약한 숙소 정보
	private long hno;
	private String homeName;
	private HomeType homeType;
	private int maximumNumber;
	private int bedNumber;
	private int bedroomNumber;
	private int bathroomNumber;
	private long homePrice;
	//예약정보
	private long resNo;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private int days;
	private int guests;
	private long totalPrice;
	//후기 정보
	private String review;
	private LocalDateTime createdDate;
	
	public ReservedHomeInfoDto (ReservationEntity entity) {
		//예약한 숙소 정보
		this.hno=entity.getHome().getHno();
		this.homeType=entity.getHome().getHomeType();
		this.homeName=entity.getHome().getHomeName();
		this.maximumNumber=entity.getHome().getMaximumNumber();
		this.bedNumber=entity.getHome().getBedNumber();
		this.bedroomNumber=entity.getHome().getBedroomNumber();
		this.bathroomNumber=entity.getHome().getBathroomNumber();
		this.homePrice=entity.getHome().getHomePrice();
		//예약정보
		this.resNo=entity.getResNo();
		this.checkIn=entity.getCheckIn();
		this.checkOut=entity.getCheckOut();
		this.days=entity.getDays();
		this.guests=entity.getGuests();
		this.totalPrice=entity.getTotalPrice();
		//후기 정보
		if(entity.getReview() !=null) {
			this.createdDate=entity.getReview().getCreatedDate();
			this.review=entity.getReview().getReview();
		}
	}
	
	
	
	
	
	
}
