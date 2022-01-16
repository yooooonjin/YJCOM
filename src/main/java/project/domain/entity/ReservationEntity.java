package project.domain.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	private long totalPrice;
	
	private String reserveStatus;
	
	@JoinColumn(name = "email")
	@ManyToOne
	private MemberEntity member;
	
	@JoinColumn(name = "hno")
	@ManyToOne
	private HomeEntity home;
	
	
}
