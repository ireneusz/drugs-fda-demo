package com.ipcode.drugsfda.domain.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Getter
@Setter
@Entity
@Table(name = "DRUG_APPLICATION")
@NoArgsConstructor(access = AccessLevel.PRIVATE)//for hibernate
public class DrugApplicationJpa implements DrugApplication {
	public static final Pattern APPLICATION_NUMBER_PATTERN = Pattern.compile("\\w{1,256}");
	@Id
	@Getter
	private String applicationNumber;

	@OneToMany(mappedBy = "drugApplication", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DrugApplicationManufacturerJpa> manufacturers = new ArrayList<>();

	@OneToMany(mappedBy = "drugApplication", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DrugApplicationSubstanceJpa> substances = new ArrayList<>();

	@OneToMany(mappedBy = "drugApplication", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DrugApplicationProductJpa> products = new ArrayList<>();

	public DrugApplicationJpa(String applicationNumber) {
		Assert.isTrue(APPLICATION_NUMBER_PATTERN.matcher(applicationNumber).matches(), "applicationNumber invalid");
		this.applicationNumber = applicationNumber;
	}

	@Override
	public List<String> getManufacturerNames() {
		return manufacturers.stream()
				.map(DrugApplicationManufacturerJpa::getManufacturerName)
				.sorted()
				.collect(toList());
	}

	@Override
	public List<String> getSubstanceNames() {
		return substances.stream()
				.map(DrugApplicationSubstanceJpa::getSubstanceName)
				.sorted()
				.collect(toList());
	}

	@Override
	public List<String> getProductNumbers() {
		return products.stream()
				.map(DrugApplicationProductJpa::getProductNumber)
				.sorted()
				.collect(toList());
	}

	public void update(Set<String> manufacturerNames, Set<String> substanceNames, Set<String> productNumbers) {
		manufacturers.clear();
		substances.clear();
		products.clear();

		emptyIfNull(manufacturerNames).stream()
				.map(manufacturerName -> new DrugApplicationManufacturerJpa(this, manufacturerName))
				.forEach(manufacturers::add);
		emptyIfNull(substanceNames).stream()
				.map(substanceName -> new DrugApplicationSubstanceJpa(this, substanceName))
				.forEach(substances::add);
		emptyIfNull(productNumbers).stream()
				.map(productNumber -> new DrugApplicationProductJpa(this, productNumber))
				.forEach(products::add);
	}
}
