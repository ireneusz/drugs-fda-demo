package com.ipcode.drugsfda.interfaces.rest.drugapplication.impl

import com.ipcode.drugsfda.application.impl.DrugApplicationInMemorySpec
import com.ipcode.drugsfda.domain.exception.DrugApplicationNotFoundException
import com.ipcode.drugsfda.domain.shared.DrugProductSearchCriteria
import com.ipcode.drugsfda.domain.shared.DrugProductSearchOrderByField
import com.ipcode.drugsfda.domain.shared.Pagination
import com.ipcode.drugsfda.interfaces.rest.drugapplication.dto.DrugApplicationStoreDto
import spock.lang.Shared
import spock.lang.Stepwise

@Stepwise
class DrugApplicationRestFacadeImplSpec extends DrugApplicationInMemorySpec {

    def drugApplicationRestFacade = new DrugApplicationRestFacadeImpl(
            drugApplicationApplicationService,
            new DrugApplicationDtoConverter()
    )

    @Shared
    def applicationNumber = 'ASDF'

    def "should store new drug application"() {
        given:
        def requestDto = new DrugApplicationStoreDto(
                applicationNumber: applicationNumber,
                productNumbers: ['P001'],
                substanceNames: ['S001', 'S001'],
                manufacturerNames: ['M1']
        )

        and:
        drugApplicationRepository.findByApplicationNumber(requestDto.applicationNumber).isEmpty()

        when:
        drugApplicationRestFacade.store(requestDto)
        def stored = drugApplicationRepository.findByApplicationNumber(requestDto.applicationNumber).orElse(null)

        then:
        verifyAll {
            stored
            stored.getApplicationNumber() == requestDto.applicationNumber
            stored.getManufacturerNames() == ['M1']
            stored.getSubstanceNames() == ['S001']
            stored.getProductNumbers() == ['P001']
        }
    }

    def "should update stored drug application"() {
        given:
        def requestDto = new DrugApplicationStoreDto(
                applicationNumber: applicationNumber,
                productNumbers: ['P002'],
                substanceNames: ['S003'],
                manufacturerNames: ['M2']
        )

        and:
        assert drugApplicationRepository.findByApplicationNumber(requestDto.applicationNumber).isPresent()

        when:
        drugApplicationRestFacade.store(requestDto)
        def stored = drugApplicationRepository.findByApplicationNumber(requestDto.applicationNumber)
                .orElse(null)

        then:
        verifyAll {
            stored
            stored.getApplicationNumber() == requestDto.applicationNumber
            stored.getManufacturerNames() == ['M2']
            stored.getSubstanceNames() == ['S003']
            stored.getProductNumbers() == ['P002']
        }
    }

    def "should search by criteria"() {
        given:
        def requestDto = new DrugApplicationStoreDto(
                applicationNumber: 'APPP3'
        )
        drugApplicationRestFacade.store(requestDto)

        def searchCriteria = new DrugProductSearchCriteria.DrugProductSearchCriteriaBuilder()
                .applicationNumber(applicationNumber).build()
        def pagination = new Pagination<>(0, 1, DrugProductSearchOrderByField.applicationNumber, Pagination.SortDir.asc)

        when:
        def result = drugApplicationRestFacade.search(searchCriteria, pagination)

        then:
        verifyAll {
            result
            result.data.size() == 1
            result.total == 1
            result.totalPages == 1
            result.data.first().applicationNumber == applicationNumber
            result.data.first().getManufacturerNames() == ['M2']
            result.data.first().getSubstanceNames() == ['S003']
            result.data.first().getProductNumbers() == ['P002']
        }
    }

    def "should find by applicationNumber"() {
        when:
        def result = drugApplicationRestFacade.find(applicationNumber)

        then:
        verifyAll {
            result.applicationNumber == applicationNumber
            result.getManufacturerNames() == ['M2']
            result.getSubstanceNames() == ['S003']
            result.getProductNumbers() == ['P002']
        }
    }

    def "find method should throw exception for given applicationNumber: #applicationNumber"() {
        when:
        drugApplicationRestFacade.find(applicationNumber)

        then:
        thrown(IllegalArgumentException)

        where:
        applicationNumber << ['', null]
    }

    def "fina method should throw exception when drug application not exist for given number"() {
        when:
        drugApplicationRestFacade.find('not_exist')

        then:
        thrown(DrugApplicationNotFoundException)
    }
}
