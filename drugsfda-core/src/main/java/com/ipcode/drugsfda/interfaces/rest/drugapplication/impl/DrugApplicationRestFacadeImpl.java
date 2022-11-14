package com.ipcode.drugsfda.interfaces.rest.drugapplication.impl;

import com.ipcode.drugsfda.application.DrugApplicationApplicationService;
import com.ipcode.drugsfda.domain.shared.DrugProductSearchCriteria;
import com.ipcode.drugsfda.domain.shared.DrugProductSearchOrderByField;
import com.ipcode.drugsfda.domain.shared.Pagination;
import com.ipcode.drugsfda.domain.shared.SearchResultPageDto;
import com.ipcode.drugsfda.interfaces.rest.drugapplication.DrugApplicationRestFacade;
import com.ipcode.drugsfda.interfaces.rest.drugapplication.dto.DrugApplicationSearchResponseDto;
import com.ipcode.drugsfda.interfaces.rest.drugapplication.dto.DrugApplicationStoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DrugApplicationRestFacadeImpl implements DrugApplicationRestFacade {

	private final DrugApplicationApplicationService drugApplicationApplicationService;
	private final DrugApplicationDtoConverter drugApplicationDtoConverter;

	@Override
	@Transactional
	public void store(DrugApplicationStoreDto drugApplicationStoreDto) {
		drugApplicationApplicationService.storeDrugApplication(
				drugApplicationStoreDto.getApplicationNumber(),
				drugApplicationStoreDto.getManufacturerNames(),
				drugApplicationStoreDto.getSubstanceNames(),
				drugApplicationStoreDto.getProductNumbers()
		);
	}

	@Override
	@Transactional(readOnly = true)
	public SearchResultPageDto<DrugApplicationSearchResponseDto> search(DrugProductSearchCriteria searchCriteria, Pagination<DrugProductSearchOrderByField> pagination) {
		var pageResult = drugApplicationApplicationService.search(searchCriteria, pagination);
		return drugApplicationDtoConverter.convertToPage(pageResult);
	}

	@Override
	@Transactional(readOnly = true)
	public DrugApplicationSearchResponseDto find(String applicationNumber) {
		var drugApplication = drugApplicationApplicationService.find(applicationNumber);
		return drugApplicationDtoConverter.convert(drugApplication);
	}
}
