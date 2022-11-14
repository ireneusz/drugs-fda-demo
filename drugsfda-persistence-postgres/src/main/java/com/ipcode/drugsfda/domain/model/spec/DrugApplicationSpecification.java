package com.ipcode.drugsfda.domain.model.spec;

import com.ipcode.drugsfda.domain.model.DrugApplication;
import com.ipcode.drugsfda.domain.model.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor(access = PRIVATE)
public class DrugApplicationSpecification implements Specification<DrugApplication> {
	private static final String WILDCARD = "%";
	private final List<Predicate> predicates = new ArrayList<>();
	private final List<SearchCriteria> criteriaList;

	public static DrugApplicationSpecification fromCriteria(List<SearchCriteria> criteriaList) {
		return new DrugApplicationSpecification(
				CollectionUtils.isNotEmpty(criteriaList) ? criteriaList : List.of());
	}

	public void addPredicate(Root<DrugApplication> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria searchCriteria) {
		switch (searchCriteria.getKey()) {
			case "applicationNumber":
				if (hasText((String) searchCriteria.getValue())) {
					predicates.add(getApplicationNumberPredicate(root, builder, (String) searchCriteria.getValue()));
				}
				break;
			case "manufacturerName":
				if (hasText((String) searchCriteria.getValue())) {
					predicates.add(getManufacturerPredicate(root, builder, (String) searchCriteria.getValue()));
				}
				break;
			case "substanceName":
				if (hasText((String) searchCriteria.getValue())) {
					predicates.add(getSubstancePredicate(root, builder, (String) searchCriteria.getValue()));
				}
				break;
			case "productNumber":
				if (hasText((String) searchCriteria.getValue())) {
					predicates.add(getProductPredicate(root, builder, (String) searchCriteria.getValue()));
				}
				break;
			default:
				break;
		}
	}
	private Predicate getManufacturerPredicate(Root<DrugApplication> root, CriteriaBuilder builder, String manufacturerName) {
		return builder.like(
				builder.lower(root.joinList("manufacturers", JoinType.LEFT)
						.get("manufacturerName")),
				WILDCARD + manufacturerName.toLowerCase() + WILDCARD);
	}

	private Predicate getApplicationNumberPredicate(Root<DrugApplication> root, CriteriaBuilder builder, String applicationNumber) {
		return builder.like(builder.lower(root.get("applicationNumber")),
				WILDCARD + applicationNumber.toLowerCase() + WILDCARD);
	}

	private Predicate getSubstancePredicate(Root<DrugApplication> root, CriteriaBuilder builder, String manufacturerName) {
		return builder.like(
				builder.lower(root.joinList("substances", JoinType.LEFT)
						.get("substanceName")),
				WILDCARD + manufacturerName.toLowerCase() + WILDCARD);
	}

	private Predicate getProductPredicate(Root<DrugApplication> root, CriteriaBuilder builder, String manufacturerName) {
		return builder.like(
				builder.lower(root.joinList("products", JoinType.LEFT)
						.get("productNumber")),
				WILDCARD + manufacturerName.toLowerCase() + WILDCARD);
	}

	@Override
	public Predicate toPredicate(Root<DrugApplication> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		for (SearchCriteria criteria : criteriaList) {
			addPredicate(root, query, builder, criteria);
		}
		return builder.and(predicates.toArray(new Predicate[0]));
	}

}
