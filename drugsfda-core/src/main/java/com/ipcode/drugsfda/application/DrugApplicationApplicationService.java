package com.ipcode.drugsfda.application;

import com.ipcode.drugsfda.domain.model.DrugApplication;
import com.ipcode.drugsfda.domain.shared.DrugProductSearchCriteria;
import com.ipcode.drugsfda.domain.shared.DrugProductSearchOrderByField;
import com.ipcode.drugsfda.domain.shared.Pagination;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface DrugApplicationApplicationService {

	void storeDrugApplication(String applicationNumber, Set<String> manufacturerNames, Set<String> substanceNames, Set<String> productNumbers);

	Page<DrugApplication> search(DrugProductSearchCriteria searchCriteria, Pagination<DrugProductSearchOrderByField> pagination);

	DrugApplication find(String applicationNumber);

}
