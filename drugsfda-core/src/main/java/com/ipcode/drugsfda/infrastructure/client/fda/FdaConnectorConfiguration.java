package com.ipcode.drugsfda.infrastructure.client.fda;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class FdaConnectorConfiguration {

	private final FdaClientProperties fdaClientProperties;

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	@Bean
	ErrorDecoder errorDecoder() {
		return new FdaConnectorErrorDecoder();
	}

	@Bean
	RequestInterceptor basicAuthRequestInterceptor() {
		return new FdaApiKeyInterceptor(fdaClientProperties.getKeyValue());
	}

}
