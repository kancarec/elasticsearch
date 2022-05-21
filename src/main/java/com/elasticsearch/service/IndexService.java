package com.elasticsearch.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elasticsearch.model.Product;
import com.elasticsearch.util.DataUtil;
import com.elasticsearch.util.EsConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IndexService {
	@Autowired
	RestHighLevelClient client;

	public String createIndex() {
		String message = EsConstants.SUCCESS_MSG;
		try {
			BulkRequest request = new BulkRequest();
			List<Product> productList = DataUtil.getProductList();
			for (Product product : productList) {
				Map<String, Object> jsonMap = new HashMap<>();
				jsonMap.put("name", product.getName());
				jsonMap.put("description", product.getDescription());
				jsonMap.put("manufacturer", product.getManufacturer());
				IndexRequest indexRequest = new IndexRequest(EsConstants.INDEX_NAME, EsConstants.INDEX_TYPE_NAME,
						UUID.randomUUID().toString().toLowerCase()).source(jsonMap);
				request.add(indexRequest);
			}
			BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
		} catch (Exception e) {
			log.error(EsConstants.ERROR, e);

		}
		return message;
	}

	public void deleteIndex() {
		try {
			DeleteIndexRequest request = new DeleteIndexRequest(EsConstants.INDEX_NAME);
			client.indices().delete(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error(EsConstants.ERROR, e);
		}
	}

}
