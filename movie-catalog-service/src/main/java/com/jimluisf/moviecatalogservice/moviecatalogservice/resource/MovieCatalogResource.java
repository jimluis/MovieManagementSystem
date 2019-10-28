package com.jimluisf.moviecatalogservice.moviecatalogservice.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.jimluisf.moviecatalogservice.moviecatalogservice.models.CatalogItem;
import com.jimluisf.moviecatalogservice.moviecatalogservice.models.Movie;
import com.jimluisf.moviecatalogservice.moviecatalogservice.models.Rating;
import com.jimluisf.moviecatalogservice.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource 
{
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder  webClientBuilder;
	
		@GetMapping("/{userId}")
		public List<CatalogItem> getCatalog(@PathVariable String userId)
		{
			List<CatalogItem> catalogItemList = new ArrayList<CatalogItem>();

			UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingdata/users/"+userId, UserRating.class);
			
			
			for (Rating rating2 : userRating.getUserRating()) 
			{
				//for each movieId call movie into service and get details
				Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+rating2.getMovieId(), Movie.class);
				
				//put them all together
				catalogItemList.add(new CatalogItem(movie.getName(), "Desc", rating2.getRating()));
			}
			
			return catalogItemList;

			
		}
}


//return ratings.stream().map(rating -> {
//Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);

//Movie movie = webClientBuilder.build()
//.get()//get method, if post is needed call post()
//.uri("http://localhost:8082/movies/"+rating.getMovieId())
//.retrieve()
//.bodyToMono(Movie.class)//This is a promise
//.block(); // block waits until the promise returns

//return new CatalogItem(movie.getName(), "Desc", rating.getRating());
//})
//
//.collect(Collectors.toList());


