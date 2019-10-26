package com.jimluisf.moviecatalogservice.moviecatalogservice.resource;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jimluisf.moviecatalogservice.moviecatalogservice.models.CatalogItem;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource 
{

		@GetMapping("/{userId}")
		public List<CatalogItem> getCatalog(@PathVariable String userId)
		{
			return Collections.singletonList(new CatalogItem("Transformers", "Test", 4));
		}
}
