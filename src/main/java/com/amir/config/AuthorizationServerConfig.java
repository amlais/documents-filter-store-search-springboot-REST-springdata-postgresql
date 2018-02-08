package com.amir.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//
@Configuration
@EnableAuthorizationServer
//extends AuthorizationServerConfigurerAdapter
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security){
		
		security.checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
		clients
			.inMemory()
			.withClient("upload-client")
			.authorizedGrantTypes("client-credentials", "password")
			.authorities("ROLE_ADMIN", "ROLE_USER")
			.scopes("read", "write")
			.resourceIds("oauth2-resource")
			.accessTokenValiditySeconds(5000)
			.secret("uploadpass");
	}
	//Added to enable GrantType password is not done by defalut spring boot configuration
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception{
		endpoints.authenticationManager(authenticationManager);
	}
}
