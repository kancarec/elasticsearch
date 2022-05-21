package com.elasticsearch.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.MultiSearchResponse.Item;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elasticsearch.model.SearchDTO;
import com.elasticsearch.util.EsConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SearchService {
	@Autowired
	RestHighLevelClient client;

	public String multisearch(List<SearchDTO> searchList) {

		String searchResponse = EsConstants.EMPTY_STRING;
		MultiSearchRequest request = getMultiSearchRequest(searchList);
		try {
			MultiSearchResponse response = client.msearch(request, RequestOptions.DEFAULT);
			for (Item item : response.getResponses()) {
				for (SearchHit hit : item.getResponse().getHits()) {
					log.info(hit.getSourceAsString() + EsConstants.SCORE + hit.getScore());
					searchResponse += EsConstants.BLANK_STRING + hit.getSourceAsString() + EsConstants.NEW_LINE
							+ EsConstants.SCORE + hit.getScore() + EsConstants.NEW_LINE;
				}

			}
		} catch (IOException e) {
			log.error(EsConstants.ERROR, e);
		}
		return searchResponse;
	}

	private MultiSearchRequest getMultiSearchRequest(List<SearchDTO> searchList) {
		MultiSearchRequest request = new MultiSearchRequest();
		if (!CollectionUtils.isEmpty(searchList)) {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			for (SearchDTO searchDTO : searchList) {
				SearchRequest searchRequest = new SearchRequest(EsConstants.INDEX_NAME);
				searchSourceBuilder = new SearchSourceBuilder();
				searchSourceBuilder
						.query(QueryBuilders.matchQuery(searchDTO.getSearchField(), searchDTO.getSearchValue()));
				searchRequest.source(searchSourceBuilder);
				request.add(searchRequest);
			}
		}
		return request;
	}

	public String searchByOr(List<SearchDTO> searchList) {
		String searchResponse = EsConstants.EMPTY_STRING;
		SearchTemplateRequest request = new SearchTemplateRequest();
		request.setRequest(new SearchRequest(EsConstants.INDEX_NAME));
		request.setScriptType(ScriptType.INLINE);
		request.setScript(EsConstants.OR_SCRIPT);
		Map<String, Object> scriptParams = new HashMap<>();
		int index = 1;
		for (SearchDTO dto : searchList) {
			scriptParams.put(EsConstants.FIELD + index, dto.getSearchField());
			scriptParams.put(EsConstants.VALUE + index, dto.getSearchValue());
			index++;
		}
		request.setScriptParams(scriptParams);
		try {
			SearchTemplateResponse response = client.searchTemplate(request, RequestOptions.DEFAULT);
			for (SearchHit hit : response.getResponse().getHits()) {
				log.info(hit.getSourceAsString());
				searchResponse += EsConstants.BLANK_STRING + hit.getSourceAsString() + EsConstants.SCORE
						+ hit.getScore() + EsConstants.NEW_LINE;
			}
		} catch (IOException e) {
			log.error(EsConstants.ERROR, e);
		}
		return searchResponse;

	}

}
