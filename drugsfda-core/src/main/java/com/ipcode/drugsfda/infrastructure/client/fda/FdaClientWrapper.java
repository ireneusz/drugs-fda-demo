package com.ipcode.drugsfda.infrastructure.client.fda;

import com.ipcode.drugsfda.domain.exception.FdaSystemNotFoundResultsException;
import com.ipcode.drugsfda.infrastructure.client.fda.dto.DrugsFdaSearchResponse;

public interface FdaClientWrapper {

	DrugsFdaSearchResponse searchDrugsFda(Integer skip, Integer limit, String search, String sort) throws FdaSystemNotFoundResultsException;

}
