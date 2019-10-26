package com.jimluisf.movieinfoservice.movieinfoservice.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jimluisf.movieinfoservice.movieinfoservice.model.Movie;


@RestController
@RequestMapping("/movies")
public class MovieResource 
{
	
	@GetMapping("/{movieId}")
	public Movie getMovieInfo(@PathVariable String movieId)
	{
		return new Movie(movieId, "Test movie");
	}
}
