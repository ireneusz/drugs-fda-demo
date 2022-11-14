package com.ipcode.drugsfda.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchCriteria {

	private String key;
	private Object value;

	public static class SearchCriteriaBuilder {
		private final Map<String, Object> criterias = new HashMap<>();

		public SearchCriteriaBuilder append(String key, Object value) {
			criterias.put(key, value);
			return this;
		}

		public List<SearchCriteria> build() {
			return criterias.entrySet().stream()
					.map(it -> new SearchCriteria(it.getKey(), it.getValue()))
					.toList();
		}
	}
}
