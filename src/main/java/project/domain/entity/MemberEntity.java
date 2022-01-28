package project.domain.entity;

import java.time.LocalDate;
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

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.domain.dto.MemberUpdateDto;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "member")
@Entity
public class MemberEntity extends BaseEntity {
	
	@Id
	private String email;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String password;
	//@Column(nullable = false)
	private String gender;
	//@Column(nullable = false)
	private String phoneNumber;
	//@Column(nullable = false)
	private LocalDate birthday;
	//@Column(nullable = false)
	private String address;
	
	private String photoName;
	
	private boolean isSocial; //소셜여부확인
	
	
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private Set<MemberRole> roleSet=new HashSet<>();
	public void addRole(MemberRole role) {
		roleSet.add(role);
	}
	
	
	public MemberEntity updatePhotoName(String photoName) {
		this.photoName=photoName;
		return this;
	}
	
	public MemberEntity updateMemberInfo(MemberUpdateDto dto) {
		this.name=dto.getName();
		this.phoneNumber=dto.getPhoneNumber();
		this.address=dto.getAddress();
		this.gender=dto.getGender();
		this.birthday=dto.getBirthday();
		return this;
	}
	
}
