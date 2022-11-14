package com.ipcode.drugsfda.infrastructure.client.fda;

import com.ipcode.drugsfda.domain.exception.FdaSystemNotFoundResultsException;
import com.ipcode.drugsfda.infrastructure.client.fda.dto.DrugsFdaSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
		name = "fdaClient",
		url = "#{fdaClientProperties.baseUrl}",
		configuration = FdaConnectorConfiguration.class
)
public interface FdaClient {

	@GetMapping("/drug/drugsfda.json")
	DrugsFdaSearchResponse searchDrugsFda(
			@RequestParam(value = "skip") Integer skip,
			@RequestParam(value = "limit") Integer limit,
			@RequestParam(value = "search") String search,
			@RequestParam(value = "sort") String sort) throws FdaSystemNotFoundResultsException;

}
