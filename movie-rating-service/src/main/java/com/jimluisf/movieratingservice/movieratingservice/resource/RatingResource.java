package com.jimluisf.movieratingservice.movieratingservice.resource;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jimluisf.movieratingservice.movieratingservice.model.Rating;
import com.jimluisf.movieratingservice.movieratingservice.model.UserRating;

@RestController
@RequestMapping("/ratingdata")
public class RatingResource 
{
	private Logger logger = LoggerFactory.getLogger(RatingResource.class);
	
	@GetMapping("/{movieId}")
	public Rating getRating(@PathVariable String movieId)
	{
		return new Rating(movieId, 4);
	}
	
	
	@GetMapping("/users/{userId}")
	public UserRating getRatingList(@PathVariable String userId)
	{
		logger.info("getRatingList() - Starts - userId: "+userId);
		
		List<Rating> ratingList = new ArrayList<Rating>();
		ratingList.add(new Rating("550", 4));
		ratingList.add(new Rating("551", 3));
		
		UserRating userRating = new UserRating();
		userRating.setUserRatingList(ratingList);
		
		logger.info("getRatingList() - Ends - ratingList: "+ratingList.size());
		return userRating;
	}
}
