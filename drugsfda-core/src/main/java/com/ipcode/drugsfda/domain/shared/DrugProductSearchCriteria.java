package com.ipcode.drugsfda.domain.shared;

import com.ipcode.drugsfda.domain.model.SearchCriteria;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class DrugProductSearchCriteria {

	String applicationNumber;
	String manufacturerName;
	String substanceName;
	String productNumber;

	public List<SearchCriteria> toCriteriaList() {
		return new SearchCriteria.SearchCriteriaBuilder()
				.append("applicationNumber", applicationNumber)
				.append("manufacturerName", manufacturerName)
				.append("substanceName", substanceName)
				.append("productNumber", productNumber)
				.build();
	}
}
