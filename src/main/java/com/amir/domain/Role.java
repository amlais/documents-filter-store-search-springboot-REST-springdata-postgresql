package com.amir.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	String name;
	Role(){}
	public Role(Long id, String name){ this.id=id; this.name=name;};
	public Role(String name){ this.name=name;}
	public String getName(){ return name;}
	public void setName(String name){ this.name=name;}
}
