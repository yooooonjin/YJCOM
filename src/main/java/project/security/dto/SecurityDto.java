package project.security.dto;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import project.domain.entity.MemberEntity;

@Getter
public class SecurityDto extends User implements OAuth2User  {
	
	private String name;
	private boolean isSocial;
	private Map<String, Object> attributes;
	
	private String photoName;
	
	//public SecurityDto(String username, String password,String name, Collection<? extends GrantedAuthority> authorities) {
	public SecurityDto(MemberEntity memberEntity) {
		super(memberEntity.getEmail(), memberEntity.getPassword(),
				memberEntity.getRoleSet().stream().map(role->new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toSet()));
		
		this.name=memberEntity.getName();
		isSocial=memberEntity.isSocial();
		
		this.photoName=memberEntity.getPhotoName();
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}

}
