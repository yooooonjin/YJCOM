package project.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import project.security.service.CustomOAuth2UserService;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Lazy //순환 종속성 : @Lazy추가
	@Autowired
	private  CustomOAuth2UserService customOAuth2UserService;
	//CommonOAuth2Provider auth2Provider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			
		http.authorizeHttpRequests()// 요청에 의한 보안검사 시작
			.antMatchers("/","/login","/join","/home/**").permitAll() //인증필요없이 누구나 접근가능
			.antMatchers("/host/**").hasRole("HOST") //HOST 권한
			.antMatchers("/member/**").hasRole("USER") //USER 권한
			.antMatchers("/admin/**").hasRole("ADMIN") //ADMIN 권한
			.anyRequest().authenticated();//어떤 요청에도 보안검사
			
		http.formLogin()//보안 검증은 formLogin방식
//			.loginPage("/login")
			.loginProcessingUrl("/login")//로그인페이지의 action
			.usernameParameter("email")//로그인페이지 username->email
			;
		
		http.oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
		
		http.logout().logoutUrl("/logout")
					.logoutSuccessUrl("/")
					;
		
		//http.csrf().disable();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**");
		web.ignoring().antMatchers("/image/**");
		web.ignoring().antMatchers("/js/**");
	}

	
	
}
