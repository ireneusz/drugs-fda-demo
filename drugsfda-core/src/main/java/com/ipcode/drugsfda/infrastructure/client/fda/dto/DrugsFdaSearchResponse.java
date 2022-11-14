package com.ipcode.drugsfda.infrastructure.client.fda.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DrugsFdaSearchResponse {

	public static final DrugsFdaSearchResponse NO_RESULT_RESPONSE = new DrugsFdaSearchResponse();

	private Meta meta;
	private List<Result> results = null;

	@Data
	@NoArgsConstructor
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Result {

		private String applicationNumber;
		private List<Product> products;
		private Openfda openfda;

	}

	@Data
	@NoArgsConstructor
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Openfda {

		private List<String> brandName;
		private List<String> manufacturerName;
		private List<String> substanceName;

	}
	@Data
	@NoArgsConstructor
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Product {
		private String productNumber;
	}

	@Data
	@NoArgsConstructor
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Meta {

		private MetaResults results;

	}
	@Data
	@NoArgsConstructor
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class MetaResults {
		private Integer skip;
		private Integer limit;
		private Integer total;
	}

}
