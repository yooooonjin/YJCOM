package project.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="HomeImage")
@Entity
public class HomeImageEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ino;
	
	@Column(nullable = false)
	private String fileOrgName;
	@Column(nullable = false)
	private String fileNewName;
	@Column(nullable = false)
	private long fileSize;
	
	private int orderNo;
	
	@JoinColumn(name = "hno")
	@ManyToOne(fetch = FetchType.LAZY)
	private HomeEntity home;
	
	

}
