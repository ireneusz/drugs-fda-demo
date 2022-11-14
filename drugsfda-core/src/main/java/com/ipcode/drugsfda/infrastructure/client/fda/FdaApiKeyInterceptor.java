package com.ipcode.drugsfda.infrastructure.client.fda;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FdaApiKeyInterceptor implements RequestInterceptor {

	private static final String API_KEY_PARAM_NAME = "api_key";

	private final String keyValue;

	@Override
	public void apply(RequestTemplate template) {
		template.query(API_KEY_PARAM_NAME, keyValue);
	}

}
