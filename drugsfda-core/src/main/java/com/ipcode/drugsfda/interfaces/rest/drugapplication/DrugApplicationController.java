package com.ipcode.drugsfda.interfaces.rest.drugapplication;

import com.ipcode.drugsfda.domain.shared.DrugProductSearchCriteria;
import com.ipcode.drugsfda.domain.shared.DrugProductSearchOrderByField;
import com.ipcode.drugsfda.domain.shared.Pagination;
import com.ipcode.drugsfda.domain.shared.SearchResultPageDto;
import com.ipcode.drugsfda.interfaces.rest.drugapplication.dto.DrugApplicationSearchRequestDto;
import com.ipcode.drugsfda.interfaces.rest.drugapplication.dto.DrugApplicationSearchResponseDto;
import com.ipcode.drugsfda.interfaces.rest.drugapplication.dto.DrugApplicationStoreDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import static com.ipcode.drugsfda.infrastructure.spring.SwaggerConfig.ACCESS_TOKEN_AUTHORIZATION;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/drugApplication")
public class DrugApplicationController {
	private final DrugApplicationRestFacade drugApplicationRestFacade;

	@Operation(
			security = {@SecurityRequirement(name = ACCESS_TOKEN_AUTHORIZATION)},
			summary = "Store drug record application",
			description = "If record already exist in local storage for given applicationNumber, method will update data."
	)
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", description = "Drug record application stored"),
					@ApiResponse(responseCode = "400", description = "ERROR - at least one request parameter is not valid"),
					@ApiResponse(responseCode = "500", description = "ERROR - search error <br/>" +
							"* errorCode: APP_INTERNAL_SERVER_ERROR - application error")
			}
	)
	@PutMapping
	void store(@RequestBody @Valid DrugApplicationStoreDto drugApplicationStoreDto) {
		drugApplicationRestFacade.store(drugApplicationStoreDto);
	}

	@Operation(
			security = {@SecurityRequirement(name = ACCESS_TOKEN_AUTHORIZATION)},
			summary = "Find drug applications stored locally")
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", description = "Drug applications data returned"),
					@ApiResponse(responseCode = "400", description = "ERROR - at least one request parameter is not valid"),
					@ApiResponse(responseCode = "500", description = "ERROR - search error <br/>" +
							"* errorCode: APP_INTERNAL_SERVER_ERROR - application error")
			}
	)
	@GetMapping("/search")
	SearchResultPageDto<DrugApplicationSearchResponseDto> search(
			@ParameterObject @ModelAttribute DrugApplicationSearchRequestDto searchRequestDto,
			@RequestParam(defaultValue = "0") @Min(0) Integer page,
			@RequestParam(defaultValue = "10") @Min(1) @Max(1000) Integer size,
			@RequestParam(name = "orderBy", defaultValue = "applicationNumber") DrugProductSearchOrderByField orderField,
			@RequestParam(name = "orderDir", defaultValue = "desc") Pagination.SortDir direction) {

		var searchCriteria = DrugProductSearchCriteria.builder()
				.applicationNumber(searchRequestDto.getApplicationNumber())
				.manufacturerName(searchRequestDto.getManufacturerName())
				.substanceName(searchRequestDto.getSubstanceName())
				.productNumber(searchRequestDto.getProductNumber()).build();

		return drugApplicationRestFacade.search(searchCriteria, new Pagination<>(page, size, orderField, direction));
	}

	@Operation(
			security = {@SecurityRequirement(name = ACCESS_TOKEN_AUTHORIZATION)},
			summary = "Find drug application for given number")
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", description = "Drug application data returned"),
					@ApiResponse(responseCode = "400", description = "ERROR - applicationNumber not valid"),
					@ApiResponse(responseCode = "404", description = "ERROR - drug application not found for given number"),
					@ApiResponse(responseCode = "500", description = "ERROR - search error <br/>" +
							"* errorCode: APP_INTERNAL_SERVER_ERROR - application error")
			}
	)
	@GetMapping
	DrugApplicationSearchResponseDto find(@RequestParam @Size(min = 1) String applicationNumber) {
		return drugApplicationRestFacade.find(applicationNumber);
	}

}