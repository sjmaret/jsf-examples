package com.example.scopes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CounterOne {
	
	private int counter = 0;
	
	public CounterOne() {}
	
	public String increment() {
		
		counter++;
		
		return "student_two";
	}
	
	public int counter() {
		return counter;
	}

}
