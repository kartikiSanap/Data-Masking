package com.example.demo.controller;

//Class created for displaying column data after instantiating the class.
public class Column {
	
	String name;
	String status;
	String columntype;
	String addingstatus;
	public String getAddingstatus() {
		return addingstatus;
	}
	public void setAddingstatus(String addingstatus) {
		this.addingstatus = addingstatus;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getColumntype() {
		return columntype;
	}
	public void setColumntype(String columntype) {
		this.columntype = columntype;
	}
	public Column() {
		
	}
}
