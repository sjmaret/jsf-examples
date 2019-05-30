package com.example.conditional.navigation;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class TourBean {
	
	private String tourType;
	
	public TourBean() {}

	public String getTourType() {
		return tourType;
	}

	public void setTourType(String tourType) {
		this.tourType = tourType;
	}
	
	public String startTour() {
		
		if (tourType != null && tourType.equals("city")) {
			return "city_tour";
		} else {
			return "country_tour";
		}
	}

}
