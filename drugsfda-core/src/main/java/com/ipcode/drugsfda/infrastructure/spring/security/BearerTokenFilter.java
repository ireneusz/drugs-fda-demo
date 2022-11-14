package com.ipcode.drugsfda.infrastructure.spring.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class BearerTokenFilter extends HttpFilter {

	public static final String AUTHORIZATION_HEADER_NOT_FOUND_ERROR_MESSAGE = "Authorization header not found";
	public static final String INVALID_AUTHORIZATION_HEADER_ERROR_MESSAGE = "Invalid Authorization header";
	public static final String SECURITY_KEY_PREFIX = "Bearer ";

	private final String expectedAuthorisationHeaderValue;
	private final RequestMatcher requestMatcher;

	public BearerTokenFilter(final String expectedApiKey, final RequestMatcher requestMatcher) {
		this.expectedAuthorisationHeaderValue = SECURITY_KEY_PREFIX + expectedApiKey;
		this.requestMatcher = requestMatcher;
	}

	@Override
	public void doFilter(final HttpServletRequest httpRequest,
						 final HttpServletResponse httpResponse,
						 final FilterChain chain) throws IOException, ServletException {
		if (!requestMatcher.matches(httpRequest)) {
			log.trace("Resource {} is not protected by this filter", httpRequest.getRequestURI());
			chain.doFilter(httpRequest, httpResponse);
			return;
		}
		final String headerValue = httpRequest.getHeader(AUTHORIZATION);

		if (headerValue == null) {
			log.warn("Authorization header not found from {}", httpRequest.getRemoteAddr());
			httpResponse.sendError(SC_UNAUTHORIZED, AUTHORIZATION_HEADER_NOT_FOUND_ERROR_MESSAGE);
			return;
		}

		if (!headerValue.equals(expectedAuthorisationHeaderValue)) {
			log.warn("Invalid Authorization header {} from {}", headerValue, httpRequest.getRemoteAddr());
			httpResponse.sendError(SC_UNAUTHORIZED, INVALID_AUTHORIZATION_HEADER_ERROR_MESSAGE);
			return;
		}

		chain.doFilter(httpRequest, httpResponse);
	}
}
