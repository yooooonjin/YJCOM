package project.domain.dto;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
	
	@Builder.Default
	PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	
	private String email;
	private String name;
	private String password;
	private String gender;
	private String phoneNumber;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;	
	private String address;
	
}
