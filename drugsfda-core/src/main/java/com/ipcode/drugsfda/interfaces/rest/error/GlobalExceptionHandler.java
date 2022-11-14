package com.ipcode.drugsfda.interfaces.rest.error;

import com.ipcode.drugsfda.domain.exception.DrugApplicationNotFoundException;
import com.ipcode.drugsfda.domain.exception.FdaSystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends BaseExceptionHandler {

	@ResponseBody
	@ResponseStatus(INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponseDto handleException(Exception ex, HttpServletRequest request) {
		logExceptions(ex, request.getRequestURI());
		if (ex.getCause() != null && ex.getCause() instanceof FdaSystemException) {
			return handleFdaSystemException((FdaSystemException) ex.getCause());
		}

		return ErrorResponseDto.builder()
				.errorCode("APP_INTERNAL_SERVER_ERROR")
				.errorMessage(ex.getMessage())
				.build();
	}

	@ResponseBody
	@ResponseStatus(NOT_FOUND)
	@ExceptionHandler(DrugApplicationNotFoundException.class)
	public ErrorResponseDto handleException(DrugApplicationNotFoundException ex, HttpServletRequest request) {
		logExceptions(ex, request.getRequestURI());
		return ErrorResponseDto.builder()
				.errorCode("NOT_FOUND")
				.errorMessage(ex.getMessage())
				.build();
	}

	@ResponseBody
	@ResponseStatus(BAD_REQUEST)
	@ExceptionHandler({
			IllegalArgumentException.class,
			MethodArgumentTypeMismatchException.class,
			ConstraintViolationException.class,
			MethodArgumentNotValidException.class,
			ConversionFailedException.class,
			BindException.class,
			MissingServletRequestParameterException.class
	})
	public ErrorResponseDto illegalArgumentException(Exception ex, HttpServletRequest request) {
		logExceptions(ex, request.getRequestURI());
		return ErrorResponseDto.builder()
				.errorCode("ILLEGAL_ARGUMENT")
				.errorMessage(ex.getMessage())
				.build();
	}


	public ErrorResponseDto handleFdaSystemException(FdaSystemException ex) {
		return ErrorResponseDto.builder()
				.errorCode("FDA_API_ERROR_" + ex.getStatusCode().value())
				.errorMessage(ex.getMessage())
				.build();

	}
}
