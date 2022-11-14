package com.ipcode.drugsfda.domain.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultPageDto<T> {

	private List<T> data = new ArrayList<>();

	private long total;

	private long size;

	private long totalPages;

}
