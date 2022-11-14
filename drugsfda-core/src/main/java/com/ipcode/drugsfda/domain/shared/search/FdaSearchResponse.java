package com.ipcode.drugsfda.domain.shared.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FdaSearchResponse {

	private String applicationNumber;
	private List<String> manufacturerNames;
	private List<String> substanceNames;
	private List<String> productNumbers;

}
