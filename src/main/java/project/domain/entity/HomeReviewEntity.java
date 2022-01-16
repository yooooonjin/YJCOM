package project.domain.entity;

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
@Table(name = "homeReview")
@Entity
public class HomeReviewEntity extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Rno;
	
	private String review;
	
	@JoinColumn(name = "email")
	@ManyToOne
	private MemberEntity member;
	
	@JoinColumn(name = "hno")
	@ManyToOne
	private HomeEntity home;
	

}
