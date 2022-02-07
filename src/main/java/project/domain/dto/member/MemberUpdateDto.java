package project.domain.dto.member;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

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
	private String gender;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;
	private String phoneNumber;
	private String address;
	
	

}
