package com.amir.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;
	private String username;
	private String password;
	private String lastName;
	private int active;
	private int accountNonLocked;
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;
	
	User(){}
	
	public User(String username, String password, String lastName, int active, int accountNonLocked, List<Role> roles){
		this.username =username;
		this.password = password;
		this.lastName = lastName;
		this.active = active;
		this.accountNonLocked = accountNonLocked;
		this.roles = roles;
	}
	
	public User(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.lastName = user.getLastname();
		this.active = user.getActive();
		this.accountNonLocked = user.getaccountNonLocked();
		this.roles = user.getRoles();
	}

	public String getUsername(){ return username;}
	public void setUsername(String username){ this.username=username;}
	
	public String getPassword(){ return password;}
	public void setPassword(String password){ this.password=password;}
	
	public String getLastname(){ return lastName;}
	public void setLastname(String lastname){ this.lastName=lastname;}
	
	public int getaccountNonLocked(){ return accountNonLocked;}
	public void setaccountNonLocked(int accountNonLocked){ this.accountNonLocked=accountNonLocked;}
	
	public int getActive(){ return active;}
	public void setActive(int active){ this.active=active;}
	
	public List<Role> getRoles(){ return roles;}
	public void setRoles(List<Role> roles){ this.roles=roles;}
}
