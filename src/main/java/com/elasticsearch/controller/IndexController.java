package com.elasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elasticsearch.service.IndexService;

@RestController
@RequestMapping("/index")
public class IndexController {

	@Autowired
	IndexService service;

	@GetMapping("/create")
	public String createIndex() {
		return service.createIndex();
	}

	@GetMapping("/delete")
	void deleteIndex() {
		service.deleteIndex();
	}

}
