package com.ipcode.drugsfda.infrastructure.client.fda;

import com.ipcode.drugsfda.domain.exception.FdaSystemNotFoundResultsException;
import com.ipcode.drugsfda.infrastructure.client.fda.dto.DrugsFdaSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!fda-client-mock")
@Component
@RequiredArgsConstructor
public class FdaClientWrapperImpl implements FdaClientWrapper {

	private final FdaClient fdaClient;

	@Override
	public DrugsFdaSearchResponse searchDrugsFda(Integer skip, Integer limit, String search, String sort) throws FdaSystemNotFoundResultsException {
			return fdaClient.searchDrugsFda(skip, limit, search, sort);
	}
}
