package com.ipcode.drugsfda.domain.service.impl;

import com.ipcode.drugsfda.domain.shared.Pagination;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchCriteria;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchOrderByField;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FdaRequestParamsGenerator {

	private static final String MANUFACTURER_NAME_SEARCH_PARAM = "openfda.manufacturer_name";
	private static final String BRAND_NAME_SEARCH_PARAM = "openfda.brand_name";

	public String getSearchParam(FdaSearchCriteria searchCriteria) {
		return new ParamFdaSearchStringBuilder()
				.append(MANUFACTURER_NAME_SEARCH_PARAM, searchCriteria.getManufacturerName())
				.append(BRAND_NAME_SEARCH_PARAM, searchCriteria.getBrandName())
				.build();
	}

	public String getSortValue(Pagination<FdaSearchOrderByField> pagination) {
		return String.format("%s:%s", pagination.getOrderBy().getValue(), pagination.getSortDir().name());
	}

	public int getSkipValue(Pagination<FdaSearchOrderByField> pagination) {
		return pagination.getPageSize() * pagination.getPageNumber();
	}

	private static class ParamFdaSearchStringBuilder {
		private static final String PARAM_STRING_FORMAT = "%s:\"%s\"";
		private final List<String> searchItems = new ArrayList<>();

		public ParamFdaSearchStringBuilder append(String searchKey, String searchValue) {
			if (StringUtils.isNotEmpty(searchKey) && StringUtils.isNotEmpty(searchValue)) {
				searchItems.add(String.format(PARAM_STRING_FORMAT, searchKey, searchValue));
			}

			return this;
		}

		public String build() {
			return String.join(",", searchItems);
		}

	}

}
