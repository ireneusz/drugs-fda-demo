package com.ipcode.drugsfda.domain.shared;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DrugProductSearchOrderByField implements OrderByField {

	applicationNumber("applicationNumber");

	@Getter
	private final String value;

}
