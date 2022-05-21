package com.elasticsearch.service.request;

import java.util.List;

import com.elasticsearch.model.SearchDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestSearch {
	List<SearchDTO> searchList;
}
