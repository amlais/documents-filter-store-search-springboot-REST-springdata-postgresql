package com.amir.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amir.domain.User;
import com.amir.domain.CustomUserDetails;
import com.amir.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> optionalUsers = userRepository.findByUsername(username);
		optionalUsers
			.orElseThrow(() -> new UsernameNotFoundException("Username notfound"));
		return optionalUsers
				.map(CustomUserDetails::new).get();
	}
}
