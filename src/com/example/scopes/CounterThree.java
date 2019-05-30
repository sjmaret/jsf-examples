package com.example.scopes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class CounterThree {
	
	private int counter = 0;
	
	public CounterThree() {}
	
	public String increment() {
		
		counter++;
		
		return "student_two";
	}
	
	public int counter() {
		return counter;
	}

}
