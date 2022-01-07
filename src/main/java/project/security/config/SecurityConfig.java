package project.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Override
	protected void configure(HttpSecurity http) throws Exception {
			
		http.authorizeHttpRequests()// 요청에 의한 보안검사 시작
			.antMatchers("/","/page/**").permitAll() //인증필요없이 누구나 접근가능
			.antMatchers("/member/**").hasRole("USER") //USER 권한
			.antMatchers("/admin/**").hasRole("ADMIN") //ADMIN 권한
			.anyRequest().authenticated();//어떤 요청에도 보안검사
			
		http.formLogin()//보안 검증은 formLogin방식
			.loginPage("/page/login")
			.loginProcessingUrl("/login")//로그인페이지의 action
			.usernameParameter("email")//로그인페이지 username->email
			.defaultSuccessUrl("/")
			.and().logout().logoutSuccessUrl("/")
			;
		//http.csrf().disable();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**");
	}

	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
