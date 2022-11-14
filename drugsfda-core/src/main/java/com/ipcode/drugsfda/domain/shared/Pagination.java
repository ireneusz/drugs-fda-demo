package com.ipcode.drugsfda.domain.shared;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public final class Pagination<T extends OrderByField> extends PageRequest {

	@Getter
	private final T orderBy;
	@Getter
	private final SortDir sortDir;

	public Pagination(Integer page, Integer size, T orderBy, SortDir sortDir) {
		super(page, size, Sort.by(Sort.Direction.valueOf(sortDir.name().toUpperCase()), orderBy.getValue()));
		this.orderBy = orderBy;
		this.sortDir = sortDir;
	}

	public enum SortDir {
		asc,
		desc
	}

}
