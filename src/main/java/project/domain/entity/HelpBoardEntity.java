package project.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="help_board")
@Entity
public class HelpBoardEntity extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bno;
	@Column(nullable = false)
	private String category;
	@Column(nullable = false)
	private String subject;
	@Column(nullable = false)
	private String content;
	
	private int readCount;
	
	@JoinColumn(name = "email")
	@ManyToOne
	private MemberEntity member;

}
