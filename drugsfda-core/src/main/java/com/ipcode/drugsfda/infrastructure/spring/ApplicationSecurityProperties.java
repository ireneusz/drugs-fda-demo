package com.ipcode.drugsfda.infrastructure.spring;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Slf4j
@ConfigurationProperties(prefix = "application.security")
public class ApplicationSecurityProperties {

	@Size(min = 10)
	@NotEmpty
	private String apiKeyToken;

	@NotEmpty
	private List<String> publicAccessUrls;

}