package com.ipcode.drugsfda.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class SubSystemException extends Exception {

	@Getter
	private final HttpStatus statusCode;

	public SubSystemException(String msg, HttpStatus statusCode) {
		super(msg);
		this.statusCode = statusCode;
	}

}
