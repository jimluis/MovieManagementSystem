package com.jimluisf.moviecatalogservice.moviecatalogservice.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jimluisf.moviecatalogservice.moviecatalogservice.models.Rating;
import com.jimluisf.moviecatalogservice.moviecatalogservice.models.UserRating;
import com.jimluisf.moviecatalogservice.moviecatalogservice.resource.MovieCatalogResource;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserRatingInfo 
{
	private Logger logger = LoggerFactory.getLogger(UserRatingInfo.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	//@HystrixCommand tells hystrix that this method should not cross the configured parameters, 
	//but if that is the case, call the getFallbackCatalog method
	@HystrixCommand(fallbackMethod = "getFallbackUserRating") 
	public UserRating getUserRating(String userId) 
	{
		logger.debug("getUserRating() - Starts - userId: "+userId);
		UserRating userRating = null; 
		
//		try 
//		{
			long startTime = System.currentTimeMillis();
			userRating = restTemplate.getForObject("http://ratings-data-service/ratingdata/users/"+userId, UserRating.class);
			long endTime = System.currentTimeMillis();
			
			long totalTime = endTime - startTime;
			logger.info("getUserRating() - Call duration in milliseconds: "+totalTime);
			
//		} catch (Exception e) {
//			logger.error("getUserRating() - An exception occurred while calling movie-info-service with userId: "+userId, e);
//		}
		
		
		return userRating;
	}
	
	public UserRating getFallbackUserRating(String userId) 
	{
		logger.info("getFallbackUserRating() - Starts userId: "+userId);
		
		List<Rating> userRatingList = new ArrayList<Rating>();
		
		Rating rating = new Rating();
		rating.setMovieId("0");
		rating.setRating(0);
		
		userRatingList.add(rating);
		
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setUserRatingList(userRatingList);

		return userRating;
	}
}
