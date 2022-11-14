package com.ipcode.drugsfda.interfaces.rest.drugapplication.impl;

import com.ipcode.drugsfda.domain.model.DrugApplication;
import com.ipcode.drugsfda.domain.shared.SearchResultPageDto;
import com.ipcode.drugsfda.interfaces.rest.drugapplication.dto.DrugApplicationSearchResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class DrugApplicationDtoConverter {

	public SearchResultPageDto<DrugApplicationSearchResponseDto> convertToPage(Page<DrugApplication> drugApplicationPage) {
		var content = drugApplicationPage.stream()
				.map(this::convert)
				.toList();
		return new SearchResultPageDto<>(content,
				drugApplicationPage.getTotalElements(),
				drugApplicationPage.getSize(),
				drugApplicationPage.getTotalPages()
		);
	}

	public DrugApplicationSearchResponseDto convert(DrugApplication drugApplication) {
		return DrugApplicationSearchResponseDto.builder()
				.applicationNumber(drugApplication.getApplicationNumber())
				.manufacturerNames(drugApplication.getManufacturerNames())
				.substanceNames(drugApplication.getSubstanceNames())
				.productNumbers(drugApplication.getProductNumbers())
				.build();
	}
}
