package com.jimluisf.movieratingservice.movieratingservice.resource;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
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

	@GetMapping("/{movieId}")
	public Rating getRating(@PathVariable String movieId)
	{
		return new Rating(movieId, 4);
	}
	
	
	@GetMapping("/users/{userId}")
	public UserRating getRatingList(@PathVariable String userId)
	{
		List<Rating> ratingList = new ArrayList<Rating>();
		ratingList.add(new Rating("1234", 4));
		ratingList.add(new Rating("5678", 3));
		
		UserRating userRating = new UserRating();
		userRating.setUserRating(ratingList);
		return userRating;
	}
}
