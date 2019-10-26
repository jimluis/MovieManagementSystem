package com.jimluisf.moviecatalogservice.moviecatalogservice.models;

public class CatalogItem 
{
	private String title;
	private String desc;
	private int ratings;
	
	
	
	public CatalogItem(String title, String desc, int ratings) {
		super();
		this.title = title;
		this.desc = desc;
		this.ratings = ratings;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getRatings() {
		return ratings;
	}
	public void setRatings(int ratings) {
		this.ratings = ratings;
	}
	
	
	
}
