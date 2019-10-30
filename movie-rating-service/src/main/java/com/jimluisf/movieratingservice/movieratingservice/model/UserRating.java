package com.jimluisf.movieratingservice.movieratingservice.model;

import java.util.List;


public class UserRating 
{
	private List<Rating> userRatingList;
	private String userId;
	
	public UserRating(List<Rating> userRatingList, String userId) {
		this.userRatingList = userRatingList;
		this.userId = userId;
	}

	public UserRating() {
	}
	
	public List<Rating> getUserRatingList() {
		return userRatingList;
	}

	public void setUserRatingList(List<Rating> userRating) {
		this.userRatingList = userRating;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
