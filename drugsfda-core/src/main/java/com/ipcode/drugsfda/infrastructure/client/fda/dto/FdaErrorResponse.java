package com.ipcode.drugsfda.infrastructure.client.fda.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FdaErrorResponse {

	private Error error;

	public String getErrorMessage() {
		return error != null ? error.getMessage() : null;
	}

	@Data
	public class Error {
		private String code;
		private String message;
	}

}
