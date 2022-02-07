package project.domain.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.entity.MemberEntity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberInfoDto {
	private String email;
	private String name;
	private String photoName;
	
	public MemberInfoDto (MemberEntity entity) {
		this.email=entity.getEmail();
		this.name=entity.getName();
		this.photoName=entity.getPhotoName();
	}
}
