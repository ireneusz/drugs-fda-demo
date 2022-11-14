package com.ipcode.drugsfda.infrastructure.persistence;

import com.ipcode.drugsfda.domain.model.DrugApplication;
import com.ipcode.drugsfda.domain.model.DrugApplicationJpa;
import com.ipcode.drugsfda.domain.model.DrugApplicationRepository;
import com.ipcode.drugsfda.domain.model.SearchCriteria;
import com.ipcode.drugsfda.domain.model.spec.DrugApplicationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class DrugApplicationSpringDataFacade implements DrugApplicationRepository {

	private final DrugApplicationJpaRepository drugApplicationJpaRepository;

	@Override
	public DrugApplication newInstance(String applicationNumber) {
		return new DrugApplicationJpa(applicationNumber);
	}

	@Override
	public void store(DrugApplication drugApplication) {
		drugApplicationJpaRepository.save((DrugApplicationJpa) drugApplication);
	}

	@Override
	public Optional<DrugApplication> findByApplicationNumber(String applicationNumber) {
		return drugApplicationJpaRepository.findByApplicationNumber(applicationNumber)
				.map(DrugApplication.class::cast);
	}

	@Override
	public Page<DrugApplication> search(List<SearchCriteria> searchCriteriaList, Pageable pageable) {
		var searchSpec = DrugApplicationSpecification.fromCriteria(searchCriteriaList);
		return drugApplicationJpaRepository.findAll(searchSpec, pageable);
	}

}
