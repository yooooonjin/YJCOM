package project.domain.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="home")
@Entity
public class HomeEntity extends baseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long hno;
	
	@Column(nullable = false)
	private String homeName;
	@Column(nullable = false)
	private String homeAddress;
	@Column(nullable = false)
	private long homePrice;
	
	@Column(nullable = false)
	private int maximumNumber;
	@Column(nullable = false)
	private int bedNumber;
	@Column(nullable = false)
	private int bedroomNumber;
	@Column(nullable = false)
	private int bathroomNumber;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private HomeType homeType;
	
	@Column(nullable = false)
	private String homeIntro;
	
	private String homePhoto;
	
	@Column(nullable = false)
	private String useable;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private Set<HomeOption> homeOptionSet= new HashSet<HomeOption>();
	public void addHomeOption(HomeOption homeOption) {
		homeOptionSet.add(homeOption);
	}
	
	@ManyToOne
	@JoinColumn(name = "email")
	private MemberEntity member;
	

}
