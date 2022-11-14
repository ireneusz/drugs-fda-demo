package com.ipcode.drugsfda.domain.exception;

import org.springframework.http.HttpStatus;

public class FdaSystemNotFoundResultsException extends FdaSystemException {

	public FdaSystemNotFoundResultsException(String msg, HttpStatus statusCode) {
		super(msg, statusCode);
	}

}
