package com.amir.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amir.domain.Role;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Test
	public void should_store_a_role(){
		Role role = new Role(1L,"ADMIN");
		Role savedRole = roleRepository.save(role);
		assertThat(savedRole).hasFieldOrPropertyWithValue("name", "ADMIN");
	}
	
	@Test
	 public void should_delete_all_users(){
		Role role = new Role(1L,"ADMIN");
		Role role2 = new Role(2L,"USER");
		roleRepository.save(role);
		roleRepository.save(role2);
		 
		roleRepository.deleteAll();
		 
		assertTrue(((List<Role>) roleRepository.findAll()).isEmpty());
	 }
}
