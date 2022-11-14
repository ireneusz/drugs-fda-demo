package com.ipcode.drugsfda.domain.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.regex.Pattern;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "drug_application_substance")
@NoArgsConstructor(access = AccessLevel.PRIVATE)//for hibernate
public class DrugApplicationSubstanceJpa {
	public static final Pattern SUBSTANCE_NAME_PATTERN = Pattern.compile("\\w{1,256}");
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String substanceName;
	@ManyToOne
	@JoinColumn(name = "application_number")
	private DrugApplicationJpa drugApplication;

	public DrugApplicationSubstanceJpa(DrugApplicationJpa drugApplication, String substanceName){
		Assert.isTrue(SUBSTANCE_NAME_PATTERN.matcher(substanceName).matches(), "substanceName invalid");
		Assert.notNull(drugApplication, "drugApplication mut not be null");
		this.drugApplication = drugApplication;
		this.substanceName = substanceName;
	}

}
