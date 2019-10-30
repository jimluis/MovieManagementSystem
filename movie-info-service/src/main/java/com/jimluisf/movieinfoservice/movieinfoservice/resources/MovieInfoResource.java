package com.jimluisf.movieinfoservice.movieinfoservice.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jimluisf.movieinfoservice.movieinfoservice.model.Movie;
import com.jimluisf.movieinfoservice.movieinfoservice.model.MovieSummary;


@RestController
@RequestMapping("/movies")
public class MovieInfoResource 
{
	private Logger logger = LoggerFactory.getLogger(MovieInfoResource.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${api.key}")
	private String apikey;
	
	@GetMapping("/{movieId}")
	public Movie getMovieInfo(@PathVariable String movieId)
	{
		logger.info("getMovieInfo() - Starts - movieId: "+movieId);
		Movie movie = new Movie();
		
		String url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apikey;
		logger.info("getMovieInfo() - URL: "+"https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apikey);
		
		try 
		{
			long startTime = System.currentTimeMillis();
			logger.debug("getMovieInfo() - Before making a external call to themoviedb");
			//for each movieId call movie info service and get details
			MovieSummary movieSummary = restTemplate.getForObject(url, MovieSummary.class);
			long endTime = System.currentTimeMillis();
			
			long totalTime = endTime - startTime;
			logger.info("getMovieInfo() - Call duration in milliseconds: "+totalTime);
			
			if(movieSummary != null)
			{
				movie.setMovieId(movieId);
				movie.setName(movieSummary.getTitle());
				movie.setOverView(movieSummary.getOverview());
			}
			else
				logger.info("getMovieInfo() - movieSummary is null");
				
		} catch (Exception e) {
			logger.error("getMovieInfo() - Exception while calling themoviedb with movieId: "+movieId,e);
			
		}
		
		return movie;
	}
}
//api_key=f7429f9292067134e1f9f805c4eef5fa