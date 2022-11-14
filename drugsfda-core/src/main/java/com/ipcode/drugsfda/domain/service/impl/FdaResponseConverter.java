package com.ipcode.drugsfda.domain.service.impl;

import com.ipcode.drugsfda.domain.shared.SearchResultPageDto;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchResponse;
import com.ipcode.drugsfda.infrastructure.client.fda.dto.DrugsFdaSearchResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class FdaResponseConverter {

	public SearchResultPageDto<FdaSearchResponse> convert(DrugsFdaSearchResponse fdaSearchResponse) {
		if (fdaSearchResponse.getMeta() == null ||
				fdaSearchResponse.getMeta().getResults() == null ||
				CollectionUtils.isEmpty(fdaSearchResponse.getResults())) {
			return new SearchResultPageDto<>(List.of(), 0,0,0);
		}

		var content = fdaSearchResponse.getResults().stream()
				.map(this::convertContent)
				.toList();

		var totalPages = Double.valueOf(
				Math.ceil(fdaSearchResponse.getMeta().getResults().getTotal().doubleValue() /
						fdaSearchResponse.getMeta().getResults().getLimit())
		).intValue();
		return new SearchResultPageDto<>(content,
				fdaSearchResponse.getMeta().getResults().getTotal(),
				fdaSearchResponse.getMeta().getResults().getLimit(),
				totalPages
		);
	}

	private FdaSearchResponse convertContent(DrugsFdaSearchResponse.Result contentItem) {
		var response = new FdaSearchResponse();
		response.setApplicationNumber(contentItem.getApplicationNumber());

		if (contentItem.getOpenfda() != null) {
			response.setManufacturerNames(contentItem.getOpenfda().getManufacturerName());
			response.setSubstanceNames(contentItem.getOpenfda().getSubstanceName());
		}

		if (!CollectionUtils.isEmpty(contentItem.getProducts())) {
			response.setProductNumbers(
					contentItem.getProducts().stream()
							.map(DrugsFdaSearchResponse.Product::getProductNumber)
							.toList()
			);
		}
		return response;
	}

}
