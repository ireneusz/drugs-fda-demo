package com.ipcode.drugsfda.interfaces.rest.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FdaSearchRequestDto {

	@NotEmpty
	private String manufacturerName;
	private String brandName;

}
