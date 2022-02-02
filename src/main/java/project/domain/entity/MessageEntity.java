package project.domain.entity;

import java.time.LocalDateTime;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "message")
@Entity
public class MessageEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long mno;
	
	@JoinColumn(name="sender")
	@ManyToOne
	private MemberEntity sender;
	
	@JoinColumn(name="receiver")
	@ManyToOne
	private MemberEntity receiver;
	
	@Column(nullable = false)
	private String message;
	private LocalDateTime opendate;
	
	
	public void addOpendate(LocalDateTime opendate) {
		this.opendate=opendate;
	}
	
	
	public boolean isConnectTarget(MemberEntity target) {
		if(this.sender.equals(target) || this. receiver.equals(target)) {
			return true;
		}
		return false;
	}
	
	
}
