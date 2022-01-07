package project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.entity.MemberEntity;
import project.domain.entity.MemberRole;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JoinDto {
	private String email;
	private String name;
	private String password;
	
	public MemberEntity toEntity() {
		MemberEntity entity= MemberEntity.builder().email(email).name(name).password(password).build();
		entity.addRole(MemberRole.USER);
		
		return entity;
	}
}
