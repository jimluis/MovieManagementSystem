package com.jimluisf.moviecatalogservice.moviecatalogservice.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.jimluisf.moviecatalogservice.moviecatalogservice.models.CatalogItem;
import com.jimluisf.moviecatalogservice.moviecatalogservice.models.Movie;
import com.jimluisf.moviecatalogservice.moviecatalogservice.models.Rating;
import com.jimluisf.moviecatalogservice.moviecatalogservice.models.UserRating;
import com.jimluisf.moviecatalogservice.moviecatalogservice.resource.MovieCatalogResource;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class MovieInfo 
{
	private Logger logger = LoggerFactory.getLogger(MovieInfo.class);
	@Autowired
	private RestTemplate restTemplate;
	
	//@HystrixCommand tells hystrix that this method should not cross the configured parameters, 
	//but if that is the case, call the getFallbackCatalog method
	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem") 
	public CatalogItem getCatalogItem(Rating rating) 
	{
		logger.debug("getCatalogItem() - Starts - rating.getMovieId(): "+rating.getMovieId());
		
		Movie movie = null;
		CatalogItem catalogItem = new CatalogItem();
		
//		try 
//		{
			long startTime = System.currentTimeMillis();
			logger.debug("getCatalogItem() - Before calling movie-info-service");
			//for each movieId call movie info service and get details
			movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
			long endTime = System.currentTimeMillis();
			
			long totalTime = endTime - startTime;
			logger.info("getCatalogItem() - Call duration in milliseconds: "+totalTime);
			
//		} catch (Exception e) {
//			logger.error("getCatalogItem() - An exception occurred while calling movie-info-service with movieId: "+rating.getMovieId(), e);
//		}
//		
		
		if(movie != null)
		{
			logger.info("getCatalogItem() - rating.getMovieId(): "+movie.getMovieId());
			//put them all together
			catalogItem = new CatalogItem(movie.getName(), "Desc", rating.getRating());
		}
		else
			logger.debug("getCatalogItem() - movie is null");
		
		return catalogItem;
	}
	
	
	public CatalogItem getFallbackCatalogItem(Rating rating) 
	{
		logger.info("getCatalogItem() - movie is null");
		return new CatalogItem("No movie found", "", rating.getRating());
	}
	
//	public List<CatalogItem> getFallbackCatalogItem(@PathVariable String userId)
//	{
//		List<CatalogItem> catalogItemList = new ArrayList<CatalogItem>();
//		catalogItemList.add(new CatalogItem("No movie", "", 0));
//		
//		return catalogItemList;
//	}
	
}
