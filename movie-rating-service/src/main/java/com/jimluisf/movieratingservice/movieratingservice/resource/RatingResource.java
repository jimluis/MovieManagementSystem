package com.jimluisf.movieratingservice.movieratingservice.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jimluisf.movieratingservice.movieratingservice.model.Rating;

@RestController
@RequestMapping("/ratingdata")
public class RatingResource 
{

	@GetMapping("/{movieId}")
	public Rating getRating(@PathVariable String movieId)
	{
		return new Rating(movieId, 4);
	}
}
