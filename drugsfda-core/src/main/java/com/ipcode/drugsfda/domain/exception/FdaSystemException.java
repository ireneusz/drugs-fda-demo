package com.ipcode.drugsfda.domain.exception;

import org.springframework.http.HttpStatus;

public class FdaSystemException extends SubSystemException {

	public FdaSystemException(String msg, HttpStatus statusCode) {
		super(msg, statusCode == null ? HttpStatus.INTERNAL_SERVER_ERROR : statusCode);
	}

}
