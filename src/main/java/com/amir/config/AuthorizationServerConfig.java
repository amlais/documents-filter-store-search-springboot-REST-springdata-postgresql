package com.amir.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security){
		
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
		clients
			.inMemory()
			.withClient("ClientId")
			.secret("secret")
			.authorizedGrantTypes("authorization_code")
			.scopes("user_info")
			.autoApprove(true);
	}
}
