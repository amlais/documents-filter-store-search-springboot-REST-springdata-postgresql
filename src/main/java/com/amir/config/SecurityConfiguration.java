package com.amir.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.amir.repository.UserRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		/*
		 * auth.inMemoryAuthentication().
		 * withUser("Peter").
		 * password("Peter").
		 * roles("USER");
		 */
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("**/doc/**").authenticated()
		.anyRequest().permitAll()
		.and().formLogin().permitAll();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
//	@SuppressWarnings("deprecation")
//	private PasswordEncoder getPasswordEncoder(){
//		return new PasswordEncoder(){
//			public String encode(CharSequence charSequence){
//				return charSequence.toString();
//			}
//			public boolean matches(CharSequence charSequence, String s){
//				return true;
//			}
//			@Override
//			public String encodePassword(String rawPass, Object salt) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			@Override
//			public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//		};
//	}
}
