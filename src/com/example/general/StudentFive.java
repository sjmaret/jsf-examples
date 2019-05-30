package com.example.general;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class StudentFive {
	
	private String firstName;
	private String lastName;
	private String favoriteLanguage;
	private List<String> favoriteLanguages;
	
	public StudentFive() {
		
		// populate list of languages
		favoriteLanguages = new ArrayList<>();
		favoriteLanguages.add("Java");
		favoriteLanguages.add("C#");
		favoriteLanguages.add("PHP");
		favoriteLanguages.add("Ruby");
	}
	
	public List<String> getFavoriteLanguages() {
		return favoriteLanguages;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFavoriteLanguage() {
		return favoriteLanguage;
	}
	public void setFavoriteLanguage(String favoriteLanguage) {
		this.favoriteLanguage = favoriteLanguage;
	}

}
