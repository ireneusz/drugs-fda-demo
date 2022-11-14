package com.ipcode.drugsfda.domain.shared.search;

import com.ipcode.drugsfda.domain.shared.OrderByField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FdaSearchOrderByField implements OrderByField {

	applicationNumber("application_number");

	@Getter
	private final String value;

}
