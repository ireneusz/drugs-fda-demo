package com.ipcode.drugsfda.infrastructure.client.fda;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;

@Slf4j
@Data
@ToString(exclude = "keyValue")
@Validated
@Configuration
@ConfigurationProperties("application.fda.api")
public class FdaClientProperties {

	@NotEmpty
	private String baseUrl;

	@NotEmpty
	private String keyValue;


	@PostConstruct
	private void loaded() {
		log.info("FdaClientProperties loaded: {}", this);
	}
}
