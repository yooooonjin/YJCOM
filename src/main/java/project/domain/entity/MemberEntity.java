package project.domain.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "member")
@Entity
public class MemberEntity extends baseEntity {
	
	@Id
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private Set<MemberRole> roleSet=new HashSet<>();
	public void addRole(MemberRole role) {
		roleSet.add(role);
	}
	
}
