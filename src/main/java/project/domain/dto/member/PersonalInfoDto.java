package project.domain.dto.member;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.entity.MemberEntity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonalInfoDto {
	
	private String name;
	private String gender;
	private LocalDate birthday;
	private String email;
	private String phoneNumber;
	private String address;
	
	public PersonalInfoDto(MemberEntity entity) {
		this.name=entity.getName();
		this.gender=entity.getGender();
		this.birthday=entity.getBirthday();
		this.email=entity.getEmail();
		this.phoneNumber=entity.getPhoneNumber();
		this.address=entity.getAddress();
	}
}
