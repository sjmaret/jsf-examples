package com.example.general;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class StudentSeven {
	
	private String firstName;
	private String lastName;
	private String favoriteLanguage;
	
	public StudentSeven() {
		
		// pre-populate the bean
		firstName = "Shannon";
		lastName = "Maret";
		favoriteLanguage = "Java";
		
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
