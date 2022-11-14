package com.ipcode.drugsfda.domain.exception;

public class DrugApplicationNotFoundException extends BaseException {

	public DrugApplicationNotFoundException(String applicationNumber) {
		super("DrugApplication not found for given number:" + applicationNumber);
	}

}
