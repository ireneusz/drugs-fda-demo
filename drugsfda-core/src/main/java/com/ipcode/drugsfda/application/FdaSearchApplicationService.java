package com.ipcode.drugsfda.application;

import com.ipcode.drugsfda.domain.shared.Pagination;
import com.ipcode.drugsfda.domain.shared.SearchResultPageDto;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchCriteria;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchOrderByField;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchResponse;

public interface FdaSearchApplicationService {

	SearchResultPageDto<FdaSearchResponse> search(FdaSearchCriteria searchCriteria, Pagination<FdaSearchOrderByField> pagination);

}
