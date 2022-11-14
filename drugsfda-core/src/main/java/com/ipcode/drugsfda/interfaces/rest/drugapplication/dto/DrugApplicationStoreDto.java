package com.ipcode.drugsfda.interfaces.rest.drugapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugApplicationStoreDto {
	private static final int VALUE_MAX_LENGTH = 256;

	@NotNull
	@Size(min = 1, max = 256)
	private String applicationNumber;
	private Set<String> manufacturerNames;
	private Set<String> substanceNames;
	private Set<String> productNumbers;

	@SuppressWarnings("unused")
	@AssertTrue(message = "value in manufacturerNames too long")
	private boolean isManufacturerNamesValid() {
		return emptyIfNull(manufacturerNames).stream().noneMatch(it -> it.length() > VALUE_MAX_LENGTH);
	}

	@SuppressWarnings("unused")
	@AssertTrue(message = "value in substanceNames too long")
	private boolean isSubstanceNamesValid() {
		return emptyIfNull(substanceNames).stream().noneMatch(it -> it.length() > VALUE_MAX_LENGTH);
	}

	@SuppressWarnings("unused")
	@AssertTrue(message = "value in productNumbers too long")
	private boolean isProductNumbersValid() {
		return emptyIfNull(productNumbers).stream().noneMatch(it -> it.length() > VALUE_MAX_LENGTH);
	}

}
