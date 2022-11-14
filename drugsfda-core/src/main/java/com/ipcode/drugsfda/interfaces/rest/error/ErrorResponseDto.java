package com.ipcode.drugsfda.interfaces.rest.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {

  private String errorCode;
  private String errorMessage;

}
