package com.example.scopes;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class CounterTwo {
	
	private int counter = 0;
	
	public CounterTwo() {}
	
	public String increment() {
		
		counter++;
		
		return "student_two";
	}
	
	public int counter() {
		return counter;
	}

}
