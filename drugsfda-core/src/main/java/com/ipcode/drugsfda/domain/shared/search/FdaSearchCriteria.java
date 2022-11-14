package com.ipcode.drugsfda.domain.shared.search;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FdaSearchCriteria {
	String manufacturerName;
	String brandName;
}
