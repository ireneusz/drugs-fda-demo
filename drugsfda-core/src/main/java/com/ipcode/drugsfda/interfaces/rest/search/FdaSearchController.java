package com.ipcode.drugsfda.interfaces.rest.search;

import com.ipcode.drugsfda.domain.shared.Pagination;
import com.ipcode.drugsfda.domain.shared.SearchResultPageDto;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchCriteria;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchOrderByField;
import com.ipcode.drugsfda.domain.shared.search.FdaSearchResponse;
import com.ipcode.drugsfda.interfaces.rest.search.dto.FdaSearchRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static com.ipcode.drugsfda.infrastructure.spring.SwaggerConfig.ACCESS_TOKEN_AUTHORIZATION;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/fda/search")
public class FdaSearchController {
	private final FdaSearchRestFacade fdaSearchRestFacade;

	@Operation(
			security = {@SecurityRequirement(name = ACCESS_TOKEN_AUTHORIZATION)},
			summary = "Find drug applications in openFDA")
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", description = "Drug applications data returned"),
					@ApiResponse(responseCode = "400", description = "ERROR - at least one request parameter is not valid"),
					@ApiResponse(responseCode = "500", description = "ERROR - search error <br/>" +
							"* errorCode: FDA_API_ERROR_XXX - OpenFDA Api service error. XXX - error status code returned by  OpenFDA Api <br/>" +
							"* errorCode: APP_INTERNAL_SERVER_ERROR - application error")
			}
	)
	@GetMapping
	SearchResultPageDto<FdaSearchResponse> search(
			@ParameterObject @ModelAttribute @Valid FdaSearchRequestDto searchRequestDto,
			@RequestParam(defaultValue = "0") @Min(0) Integer page,
			@RequestParam(defaultValue = "10") @Min(1) @Max(1000) Integer size,
			@RequestParam(name = "orderBy", defaultValue = "applicationNumber") FdaSearchOrderByField orderField,
			@RequestParam(name = "orderDir", defaultValue = "desc") Pagination.SortDir direction) {
		var searchCriteria = FdaSearchCriteria.builder()
				.manufacturerName(searchRequestDto.getManufacturerName())
				.brandName(searchRequestDto.getBrandName()).build();
		return fdaSearchRestFacade.search(searchCriteria, new Pagination<>(page, size, orderField, direction));
	}

}