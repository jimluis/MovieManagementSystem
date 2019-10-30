package com.jimluisf.moviecatalogservice.moviecatalogservice.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.jimluisf.moviecatalogservice.moviecatalogservice.service.MovieInfo;
import com.jimluisf.moviecatalogservice.moviecatalogservice.service.UserRatingInfo;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource 
{
	private Logger logger = LoggerFactory.getLogger(MovieCatalogResource.class);
	
	@Autowired
	private WebClient.Builder  webClientBuilder;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;
	
//		@HystrixCommand
		@GetMapping("/{userId}")
		public List<CatalogItem> getCatalog(@PathVariable String userId)
		{
			logger.debug("getCatalog() - Starts - userId: "+userId);

			List<CatalogItem> catalogItemList = new ArrayList<CatalogItem>();
			
			long startTime = System.currentTimeMillis();
			UserRating userRating = userRatingInfo.getUserRating(userId);
			
			if(userRating != null && userRating.getUserRatingList() != null && userRating.getUserRatingList().size() > 0)
			{
				logger.debug("getCatalog() - userRating.getUserRatingList().size(): "+userRating.getUserRatingList().size());
				
				for (Rating rating : userRating.getUserRatingList()) 
				{
					CatalogItem catalogItem = movieInfo.getCatalogItem(rating);
					catalogItemList.add(catalogItem);
				}
				
				long endTime = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				logger.info("getCatalog() - Total processing time in milliseconds: "+totalTime);
			}
			else
				logger.info("getCatalog() - userRating.getUserRatingList() is null");
			
			return catalogItemList;
			
		}
		
		
//		@HystrixCommand(fallbackMethod = "getFallbackCatalogItem") 
//		public CatalogItem getCatalogItem(Rating rating) 
//		{
//			logger.debug("getCatalogItem() - Starts - rating.getMovieId(): "+rating.getMovieId());
//			
//			Movie movie = null;
//			CatalogItem catalogItem = new CatalogItem();
//			
//			try 
//			{
//				long startTime = System.currentTimeMillis();
//				logger.debug("getCatalogItem() - Before calling movie-info-service");
//				//for each movieId call movie info service and get details
//				movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
//				long endTime = System.currentTimeMillis();
//				
//				long totalTime = endTime - startTime;
//				logger.info("getCatalogItem() - Call duration in milliseconds: "+totalTime);
//				
//			} catch (Exception e) {
//				logger.error("getCatalogItem() - An exception occurred while calling movie-info-service with movieId: "+rating.getMovieId(), e);
//			}
//			
//			
//			if(movie != null)
//			{
//				logger.info("getCatalogItem() - rating.getMovieId(): "+movie.getMovieId());
//				//put them all together
//				catalogItem = new CatalogItem(movie.getName(), "Desc", rating.getRating());
//			}
//			else
//				logger.debug("getCatalogItem() - movie is null");
//			
//			return catalogItem;
//		}
//		
//		
//		public CatalogItem getFallbackCatalogItem(Rating rating) 
//		{
//			logger.info("getCatalogItem() - movie is null");
//			return new CatalogItem("No movie found", "", rating.getRating());
//		}
		
		
//		@HystrixCommand(fallbackMethod = "getFallbackUserRating") 
//		public UserRating getUserRating(String userId) 
//		{
//			logger.debug("getUserRating() - Starts - userId: "+userId);
//			UserRating userRating = null; 
//			
//			try 
//			{
//				long startTime = System.currentTimeMillis();
//				userRating = restTemplate.getForObject("http://ratings-data-service/ratingdata/users/"+userId, UserRating.class);
//				long endTime = System.currentTimeMillis();
//				
//				long totalTime = endTime - startTime;
//				logger.info("getUserRating() - Call duration in milliseconds: "+totalTime);
//				
//			} catch (Exception e) {
//				logger.error("getUserRating() - An exception occurred while calling movie-info-service with userId: "+userId, e);
//			}
//			
//			
//			return userRating;
//		}
//		
//		public UserRating getFallbackUserRating(String userId) 
//		{
//			logger.info("getFallbackUserRating() - Starts userId: "+userId);
//			
//			List<Rating> userRatingList = new ArrayList<Rating>();
//			
//			Rating rating = new Rating();
//			rating.setMovieId("0");
//			rating.setRating(0);
//			
//			userRatingList.add(rating);
//			
//			UserRating userRating = new UserRating();
//			userRating.setUserId(userId);
//			userRating.setUserRatingList(userRatingList);
//
//			return userRating;
//		}

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


