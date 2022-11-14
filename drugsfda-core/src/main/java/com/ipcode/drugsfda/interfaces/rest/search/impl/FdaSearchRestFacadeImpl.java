package com.ipcode.drugsfda.interfaces.rest.search.impl;

import com.ipcode.drugsfda.application.FdaSearchApplicationService;
import com.ipcode.drugsfda.domain.shared.Pagination;
import com.ipcode.drugsfda.domain.shared.SearchResultPageDto;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchCriteria;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchOrderByField;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchResponse;
import com.ipcode.drugsfda.interfaces.rest.search.FdaSearchRestFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FdaSearchRestFacadeImpl implements FdaSearchRestFacade {

	private final FdaSearchApplicationService fdaSearchApplicationService;

	@Override
	public SearchResultPageDto<FdaSearchResponse> search(FdaSearchCriteria searchCriteria, Pagination<FdaSearchOrderByField> pagination) {
		return fdaSearchApplicationService.search(searchCriteria, pagination);
	}
}
