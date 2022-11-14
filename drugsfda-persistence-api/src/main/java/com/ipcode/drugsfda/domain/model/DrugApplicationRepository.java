package com.ipcode.drugsfda.domain.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DrugApplicationRepository {

	DrugApplication newInstance(String applicationNumber);

	void store(DrugApplication drugApplication);

	Optional<DrugApplication> findByApplicationNumber(String applicationNumber);

	Page<DrugApplication> search(List<SearchCriteria> searchCriteriaList, Pageable pageable);
}
