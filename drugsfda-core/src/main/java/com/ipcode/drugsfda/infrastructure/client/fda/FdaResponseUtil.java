package com.ipcode.drugsfda.infrastructure.client.fda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_INVALID_SUBTYPE;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Slf4j
@UtilityClass
public class FdaResponseUtil {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		OBJECT_MAPPER.registerModule(new JavaTimeModule());
		OBJECT_MAPPER.disable(WRITE_DATES_AS_TIMESTAMPS);
		OBJECT_MAPPER.disable(FAIL_ON_INVALID_SUBTYPE);
		OBJECT_MAPPER.disable(FAIL_ON_UNKNOWN_PROPERTIES);
	}

	public static <T> T getObjectFromJson(String json, Class<T> clazz) {
		try {
			return OBJECT_MAPPER.readValue(json, clazz);
		} catch (JsonProcessingException exception) {
			log.error("Parsing json failed", exception);
		}

		return null;
	}
}

