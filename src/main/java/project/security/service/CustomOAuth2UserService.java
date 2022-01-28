package project.security.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.domain.entity.MemberEntity;
import project.domain.entity.MemberEntityRepository;
import project.domain.entity.MemberRole;
import project.security.dto.SecurityDto;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService{

	private final  PasswordEncoder passwordEncoder;
	private final MemberEntityRepository memberEntityRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User= super.loadUser(userRequest);
		//소셜로그인 인증완료
		String registrationId= userRequest.getClientRegistration().getRegistrationId();
		System.out.println(registrationId);
		
		Map<String, Object> map= oauth2User.getAttributes();
		for(String key :map.keySet()) {
			System.out.println(key+" : "+map.get(key));
		}
		
		System.out.println(passwordEncoder);
		//DefaultOAuth2User //소셜유저
		//return oauth2User;
		return saveSocialUser(registrationId,oauth2User);
	}

	private OAuth2User saveSocialUser(String registrationId, OAuth2User oauth2User) {
		String email=null;
		String name=null;
		if(registrationId.equals("google")) {
			email=oauth2User.getAttribute("email");
			name=oauth2User.getAttribute("name");
		}else if(registrationId.equals("naver")) {
			
		}
		//가입여부 체크
		Optional<SecurityDto> check= memberEntityRepository.findById(email).map(SecurityDto::new);
		if(check.isPresent()) {
			return check.get();
		}
		
		//가입이 안되어 있으면 소셜정보 회원가입 처리
		MemberEntity entity=MemberEntity.builder()
				.email(email).name(name).password(passwordEncoder.encode("socialuser"+System.currentTimeMillis())).isSocial(true).photoName("default_photo.jpg")
				.build();
		entity.addRole(MemberRole.USER);
		//SecurityDto //일반유저 타입으로 리턴
		MemberEntity result= memberEntityRepository.save(entity);
		SecurityDto securityDto =new SecurityDto(result);
		
		return securityDto;
	}
	
	

}
