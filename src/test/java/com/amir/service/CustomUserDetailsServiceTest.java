package com.amir.service;


import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import com.amir.domain.Role;
import com.amir.domain.User;
import com.amir.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomUserDetailsServiceTest {
	
	@Autowired
    private CustomUserDetailsService customUserDetailsService;
 
    @MockBean
    private UserRepository userRepository;
    
    @Before
	public void setUp(){
		User user = new User("amir.lais","123456", "lais", 1, 1, Arrays.asList(new Role(1L, "ADMIN")));
		Optional<User> optionalUser = Optional.of(user);
		List<User> users = new ArrayList<>(
			    Arrays.asList(user));
		Mockito.when(userRepository.save(user))
	      .thenReturn(user);
		Mockito.when(userRepository.findByUsername("amir.lais"))
			.thenReturn(optionalUser);
		Mockito.when(userRepository.findAll())
	      .thenReturn(users);
		Mockito.doNothing().when( userRepository).delete(1L);
	}
    
    @Test
	public void testloadUserByUsername(){
    	UserDetails userDetails = customUserDetailsService.loadUserByUsername("amir.lais");
    	Collection<? extends GrantedAuthority> u = userDetails.getAuthorities();
    	assertTrue(userDetails.getUsername().equals("amir.lais"));
    	assertTrue(userDetails.isAccountNonExpired());
    	assertTrue(userDetails.isEnabled());
    	assertTrue(userDetails.isAccountNonLocked());
    	assertTrue(u.toArray()[0].toString().equals("ADMIN"));
	}
}
