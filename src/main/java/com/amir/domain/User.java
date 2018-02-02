package com.amir.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Role> roles;
	
	User(){}
	
	public User(String username, String password, List<Role> roles){
		this.username =username;
		this.password = password;
		this.roles = roles;
	}
	
	public String getUsername(){ return username;}
	public void setUsername(String username){ this.username=username;}
	public String getPassword(){ return password;}
	public void setPassword(String password){ this.password=password;}
	public List<Role> getRoles(){ return roles;}
	public void setRoles(List<Role> roles){ this.roles=roles;}
}