package com.ipcode.drugsfda.application.impl;

import com.ipcode.drugsfda.application.FdaSearchApplicationService;
import com.ipcode.drugsfda.domain.service.FdaSearchService;
import com.ipcode.drugsfda.domain.shared.Pagination;
import com.ipcode.drugsfda.domain.shared.SearchResultPageDto;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchCriteria;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchOrderByField;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FdaSearchApplicationServiceImpl implements FdaSearchApplicationService {

	private final FdaSearchService fdaSearchService;

	@Override
	public SearchResultPageDto<FdaSearchResponse> search(FdaSearchCriteria searchCriteria, Pagination<FdaSearchOrderByField> pagination) {
		return fdaSearchService.search(searchCriteria, pagination);
	}

}
