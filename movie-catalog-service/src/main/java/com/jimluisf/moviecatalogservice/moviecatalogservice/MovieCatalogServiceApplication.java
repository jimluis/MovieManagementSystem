package com.jimluisf.moviecatalogservice.moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class MovieCatalogServiceApplication {

	
	@Bean
	@LoadBalanced //This tells spring not to take the URL as an actual URL instead, to take it as the name of the microservice
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}
	
	@Bean
	public WebClient.Builder getWebClientBuilder()
	{
		return WebClient.builder();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
	}

}
