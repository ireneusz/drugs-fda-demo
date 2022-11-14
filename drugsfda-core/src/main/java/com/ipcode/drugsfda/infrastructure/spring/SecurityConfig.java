package com.ipcode.drugsfda.infrastructure.spring;


import com.ipcode.drugsfda.infrastructure.spring.security.BearerTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableConfigurationProperties(ApplicationSecurityProperties.class)
public class SecurityConfig {

	private final ApplicationSecurityProperties applicationSecurityProperties;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.httpBasic().disable()
				.csrf().disable()
				.securityContext().disable()
				.addFilterBefore(bearerTokenFilter(), BasicAuthenticationFilter.class)
				.build();
	}

	@Bean
	public BearerTokenFilter bearerTokenFilter() {
		return new BearerTokenFilter(
				applicationSecurityProperties.getApiKeyToken(),
				protectEverythingBesides(applicationSecurityProperties.getPublicAccessUrls()));
	}

	private RequestMatcher protectEverythingBesides(final Collection<String> notProtectedPatterns) {
		return new AndRequestMatcher(notProtectedPatterns.stream()
				.map(AntPathRequestMatcher::new)
				.map(NegatedRequestMatcher::new)
				.collect(toList()));
	}
}
