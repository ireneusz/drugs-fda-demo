package com.ipcode.drugsfda.domain.model;

import java.util.List;
import java.util.Set;

public interface DrugApplication {

	String getApplicationNumber();

	List<String> getManufacturerNames();

	List<String> getSubstanceNames();

	List<String> getProductNumbers();

	void update(Set<String> manufacturerNames, Set<String> substanceNames, Set<String> productNumbers);
}