package com.ipcode.drugsfda.interfaces.rest.error;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseExceptionHandler {

  protected void logExceptions(Throwable ex, String requestPath) {
    log.debug("Rest API call: {} failed with {}: {}",
        requestPath,
        ex.getClass().getName(),
        ex.getMessage());

    log.trace("Rest API call failed", ex);
  }

}
