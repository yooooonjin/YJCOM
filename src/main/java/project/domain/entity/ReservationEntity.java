package project.domain.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "reservation")
@Entity
public class ReservationEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long resNo;
	
	@Column(nullable = false)
	private LocalDate checkIn;
	@Column(nullable = false)
	private LocalDate checkOut;
	@Column(nullable = false)
	private int guests;
	
	private int days;
	
	private long totalPrice;
	
	private String reserveStatus;
	
	@JoinColumn(name = "email")
	@ManyToOne(fetch = FetchType.LAZY)
	private MemberEntity member;
	
	@JoinColumn(name = "hno")
	@ManyToOne(fetch = FetchType.LAZY)
	private HomeEntity home;
	
	@OneToOne(mappedBy = "reservation")
	private HomeReviewEntity review;
	
	
	//내가 조회할 날짜와 중복이되면 list에서 제외 시키는 필터
	public boolean isReservation(LocalDate _checkIn, LocalDate _checkOut) { //조회한 체크인날짜 //조회한 체크아웃날짜
		
		//조회한 기간과 예약된 날짜가 중복되는 것만 반환
		if(checkIn.isBefore(_checkOut.minusDays(1)) && checkOut.isAfter(_checkIn.plusDays(1))) {
			return true;
		}
		return false;
	}
	
}
