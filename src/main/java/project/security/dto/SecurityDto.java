package project.security.dto;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import project.domain.entity.MemberEntity;

public class SecurityDto extends User  {
	
	private String name;
	
	//public SecurityDto(String username, String password,String name, Collection<? extends GrantedAuthority> authorities) {
	public SecurityDto(MemberEntity memberEntity) {
		super(memberEntity.getEmail(), memberEntity.getPassword(),
				memberEntity.getRoleSet().stream().map(role->new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toSet()));
		
		this.name=memberEntity.getName();
	}

}
