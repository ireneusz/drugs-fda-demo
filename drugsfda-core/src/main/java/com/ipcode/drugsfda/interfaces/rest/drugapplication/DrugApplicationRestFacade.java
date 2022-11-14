package com.ipcode.drugsfda.interfaces.rest.drugapplication;

import com.ipcode.drugsfda.domain.shared.DrugProductSearchCriteria;
import com.ipcode.drugsfda.domain.shared.DrugProductSearchOrderByField;
import com.ipcode.drugsfda.domain.shared.Pagination;
import com.ipcode.drugsfda.domain.shared.SearchResultPageDto;
import com.ipcode.drugsfda.interfaces.rest.drugapplication.dto.DrugApplicationSearchResponseDto;
import com.ipcode.drugsfda.interfaces.rest.drugapplication.dto.DrugApplicationStoreDto;

public interface DrugApplicationRestFacade {
	void store(DrugApplicationStoreDto drugApplicationStoreDto);

	SearchResultPageDto<DrugApplicationSearchResponseDto> search(DrugProductSearchCriteria searchCriteria,
																 Pagination<DrugProductSearchOrderByField> pagination);

	DrugApplicationSearchResponseDto find(String applicationNumber);
}
