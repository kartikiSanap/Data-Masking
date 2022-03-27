package com.example.demo.controller;

//class created for displaying project details 
public class Project {
	int id;
	String name;
	
	
	
	public Project(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
