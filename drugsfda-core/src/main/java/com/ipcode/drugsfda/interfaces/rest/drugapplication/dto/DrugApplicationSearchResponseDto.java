package com.ipcode.drugsfda.interfaces.rest.drugapplication.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class DrugApplicationSearchResponseDto {

	private String applicationNumber;
	private List<String> manufacturerNames;
	private List<String> substanceNames;
	private List<String> productNumbers;

}
