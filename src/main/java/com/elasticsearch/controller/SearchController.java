package com.elasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elasticsearch.service.SearchService;
import com.elasticsearch.service.request.RequestSearch;

@RestController
@RequestMapping("/search")
public class SearchController {
	@Autowired
	SearchService service;

	@PostMapping(path = "/multisearch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	String search(@RequestBody RequestSearch request) {
		return service.multisearch(request.getSearchList());
	}

	@PostMapping(path = "/searchByOr", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	String searchOr(@RequestBody RequestSearch request) {
		return service.searchByOr(request.getSearchList());
	}
}
