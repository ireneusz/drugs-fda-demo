package com.ipcode.drugsfda.interfaces.rest.drugapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrugApplicationSearchRequestDto {

	private String applicationNumber;
	private String manufacturerName;
	private String substanceName;
	private String productNumber;

}
