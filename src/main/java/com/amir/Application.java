package com.amir;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.amir.service.CustomUserDetailsService;

@SpringBootApplication
public class Application {
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder)throws Exception{
    	builder
    	.userDetailsService(customUserDetailsService)
    	.passwordEncoder(passwordEncoder());
    }
    
    //Will be used when expose User CRUD Service.
    @Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}
