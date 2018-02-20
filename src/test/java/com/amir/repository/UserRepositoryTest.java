package com.amir.repository;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.amir.domain.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void should_store_a_user(){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode("654321");
		User user = new User("user2",hashedPassword, "lastname", 1, 1,null);
		User savedUser = userRepository.save(user);
		assertTrue(user.equals(savedUser));
	}
	
	@Test
	 public void should_delete_all_users(){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword1 = passwordEncoder.encode("654321");
		String hashedPassword2 = passwordEncoder.encode("87654321");
		User user1 = new User("user2",hashedPassword1, "lastname", 1, 1,null);
		User user2 = new User("user3",hashedPassword2, "lastname2", 1, 1,null);
		userRepository.save(user1);
		userRepository.save(user2);
		 
		userRepository.deleteAll();
		 
		assertTrue(((List<User>) userRepository.findAll()).isEmpty());
	 }
	
}
