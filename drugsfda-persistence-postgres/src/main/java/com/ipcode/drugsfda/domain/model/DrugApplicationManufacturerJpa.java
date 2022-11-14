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
@Table(name = "drug_application_manufacturer")
@NoArgsConstructor(access = AccessLevel.PRIVATE)//for hibernate
public class DrugApplicationManufacturerJpa {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String manufacturerName;
	@ManyToOne
	@JoinColumn(name = "application_number")
	private DrugApplicationJpa drugApplication;

	public DrugApplicationManufacturerJpa(DrugApplicationJpa drugApplication, String manufacturerName) {
		Assert.hasText(manufacturerName, "manufacturerName must not be empty");
		Assert.isTrue(manufacturerName.length() <= 256, "manufacturerName mx length 256");
		Assert.notNull(drugApplication, "drugApplication mut not be null");
		this.drugApplication = drugApplication;
		this.manufacturerName = manufacturerName;
	}

}
