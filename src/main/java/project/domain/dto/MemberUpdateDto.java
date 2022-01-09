package project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberUpdateDto {
	
	
	private String email;
	private String name;
	private String phoneNumber;
	private String address;
	
	

}
