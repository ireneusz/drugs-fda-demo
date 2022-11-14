package com.ipcode.drugsfda.application.impl;

import com.ipcode.drugsfda.application.DrugApplicationApplicationService;
import com.ipcode.drugsfda.domain.exception.DrugApplicationNotFoundException;
import com.ipcode.drugsfda.domain.model.DrugApplication;
import com.ipcode.drugsfda.domain.model.DrugApplicationRepository;
import com.ipcode.drugsfda.domain.shared.DrugProductSearchCriteria;
import com.ipcode.drugsfda.domain.shared.DrugProductSearchOrderByField;
import com.ipcode.drugsfda.domain.shared.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class DrugApplicationApplicationServiceImpl implements DrugApplicationApplicationService {

	private final DrugApplicationRepository drugApplicationRepository;

	@Override
	public void storeDrugApplication(String applicationNumber, Set<String> manufacturerNames, Set<String> substanceNames, Set<String> productNumbers) {
		var drugApplication = drugApplicationRepository.findByApplicationNumber(applicationNumber)
				.orElseGet(() -> drugApplicationRepository.newInstance(applicationNumber));
		drugApplication.update(manufacturerNames, substanceNames, productNumbers);
		drugApplicationRepository.store(drugApplication);
		log.info("Drug Application [{}] stored", drugApplication);
	}

	@Override
	public Page<DrugApplication> search(DrugProductSearchCriteria searchCriteria, Pagination<DrugProductSearchOrderByField> pagination) {
		return drugApplicationRepository.search(searchCriteria.toCriteriaList(), pagination);
	}

	@Override
	public DrugApplication find(String applicationNumber) {
		Assert.hasText(applicationNumber, "applicationNumber must not be null");
		return drugApplicationRepository.findByApplicationNumber(applicationNumber)
				.orElseThrow(()-> new DrugApplicationNotFoundException(applicationNumber));
	}

}
