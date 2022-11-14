package com.ipcode.drugsfda.domain.service.impl;

import com.ipcode.drugsfda.domain.exception.FdaSystemNotFoundResultsException;
import com.ipcode.drugsfda.domain.service.FdaSearchService;
import com.ipcode.drugsfda.domain.shared.Pagination;
import com.ipcode.drugsfda.domain.shared.SearchResultPageDto;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchCriteria;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchOrderByField;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchResponse;
import com.ipcode.drugsfda.infrastructure.client.fda.FdaClientWrapper;
import com.ipcode.drugsfda.infrastructure.client.fda.dto.DrugsFdaSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FdaSearchServiceImpl implements FdaSearchService {

	private final FdaClientWrapper fdaClient;
	private final FdaRequestParamsGenerator fdaRequestParamsGenerator;
	private final FdaResponseConverter fdaResponseConverter;

	@Override
	public SearchResultPageDto<FdaSearchResponse> search(FdaSearchCriteria searchCriteria, Pagination<FdaSearchOrderByField> pagination) {
		var skipValue = fdaRequestParamsGenerator.getSkipValue(pagination);
		var searchValue = fdaRequestParamsGenerator.getSearchParam(searchCriteria);
		var sortValue = fdaRequestParamsGenerator.getSortValue(pagination);
		var limitValue = pagination.getPageSize();

		DrugsFdaSearchResponse response;
		try {
			response = fdaClient.searchDrugsFda(skipValue, limitValue, searchValue, sortValue);
		} catch (FdaSystemNotFoundResultsException ex) {
			response = DrugsFdaSearchResponse.NO_RESULT_RESPONSE;
		}

		return fdaResponseConverter.convert(response);
	}
}
