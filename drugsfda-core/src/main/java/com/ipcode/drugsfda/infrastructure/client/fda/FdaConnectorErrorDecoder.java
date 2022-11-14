package com.ipcode.drugsfda.infrastructure.client.fda;

import com.google.common.io.CharStreams;
import com.ipcode.drugsfda.domain.exception.FdaSystemException;
import com.ipcode.drugsfda.domain.exception.FdaSystemNotFoundResultsException;
import com.ipcode.drugsfda.infrastructure.client.fda.dto.FdaErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.Charset;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

@Slf4j
public class FdaConnectorErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		var body = response.body();

		String strBody = null;
		if (body == null) {
			log.error("Fda api rejected request with empty body and status: {}", response.status());
		} else {
			try {
				strBody = CharStreams.toString(body.asReader(Charset.defaultCharset()));
			} catch (IOException e) {
				return new FdaSystemException(response.reason(), HttpStatus.resolve(response.status()));
			}
			log.error("Fda api rejected request with body: {} and status: {}", strBody, response.status());
		}

		var parsedResponse = strBody != null
				? FdaResponseUtil.getObjectFromJson(strBody, FdaErrorResponse.class)
				: null;
		var responseMsg = parsedResponse != null
				? parsedResponse.getErrorMessage()
				: response.reason();

		if (response.status() == HTTP_NOT_FOUND) {
			return new FdaSystemNotFoundResultsException(responseMsg, HttpStatus.resolve(response.status()));
		}

		return new FdaSystemException(responseMsg, HttpStatus.resolve(response.status()));
	}
}
