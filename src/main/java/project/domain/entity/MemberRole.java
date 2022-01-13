package project.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberRole {
	
	USER("ROLE_USER","회원"),
	HOST("ROLE_HOST","호스트"),
	ADMIN("ROLE_ADMIN","관리자");
	
	final String role;
	final String title;

}
