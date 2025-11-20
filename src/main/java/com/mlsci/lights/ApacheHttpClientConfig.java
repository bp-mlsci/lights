package com.mlsci.lights;

import java.time.Duration;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApacheHttpClientConfig {

	// see https://springframework.guru/using-resttemplate-with-apaches-httpclient/
	
	
	
	
	@Bean
	CloseableHttpClient httpClient() {
		// note that the default pooling and connection management behaviour for 
		// the apache http client 5.x are good (enough!)
		var cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(5);
		//return HttpClients.custom()
		//		.setConnectionManager(cm)
		//		.build();
		return  HttpClientBuilder.create()
				.setConnectionManager(cm)
				.build();

	         
	}
	
	
	
	@Bean
	HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
	  HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
	  clientHttpRequestFactory.setHttpClient(httpClient());
	  return clientHttpRequestFactory;
	}
	
	
	@Bean
	RestTemplate restTemplate() {
	  return new RestTemplateBuilder()
	          .requestFactory(this::clientHttpRequestFactory)
	          .connectTimeout(Duration.ofMillis(2000)) // MILLI seconds connection timeout
              .readTimeout(Duration.ofMillis(3500)) // MILLI seconds read timeout
	          .build();
	}
	
	
}
