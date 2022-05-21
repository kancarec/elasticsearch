package com.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsConfig {
	@Value("${spring.elasticsearch.hostname}")
	private String hostName;
	@Value("${spring.elasticsearch.firstport}")
	private Integer firstPort;
	@Value("${spring.elasticsearch.secondport}")
	private Integer secondPort;
	@Value("${spring.elasticsearch.protocol}")
	private String protocol;

	@Bean
	RestHighLevelClient getClient() {
		return new RestHighLevelClient(RestClient.builder(new HttpHost(hostName, firstPort, protocol),
				new HttpHost(hostName, secondPort, protocol)));
	}
}
