package com.amir.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {
	 /*@GenericGenerator(
	        name = "wikiSequenceGenerator",
	        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	        parameters = {
	                @Parameter(name = "sequence_name", value = "WIKI_SEQUENCE"),
	                @Parameter(name = "initial_value", value = "1"),
	                @Parameter(name = "increment_size", value = "1")
	        }
	)*/
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	String name;
	Role(){}
	
	public Role(String name){ this.name=name;}
	public String getName(){ return name;}
	public void setName(String name){ this.name=name;}
}
