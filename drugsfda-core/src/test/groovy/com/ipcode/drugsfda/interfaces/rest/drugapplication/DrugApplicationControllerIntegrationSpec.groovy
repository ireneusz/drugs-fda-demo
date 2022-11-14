package com.ipcode.drugsfda.interfaces.rest.drugapplication

import com.ipcode.drugsfda.EndToEndSpecification
import com.ipcode.drugsfda.domain.model.DrugApplicationRepository
import com.ipcode.drugsfda.interfaces.rest.drugapplication.dto.DrugApplicationStoreDto
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared
import spock.lang.Stepwise

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.UNAUTHORIZED

@Stepwise
class DrugApplicationControllerIntegrationSpec extends EndToEndSpecification {

    @Autowired
    DrugApplicationRepository drugApplicationRepository

    @Shared
    def applicationNumber = randomAlphabetic(6)

    def "should not store new drug application when request data is invalid: #testCase"() {
        when:
        def storeApplicationResponse = put("/drugApplication", requestDto)

        then:
        assertResponseStatus(storeApplicationResponse, BAD_REQUEST)

        verifyAll {
            storeApplicationResponse
            storeApplicationResponse.body.errorCode == 'ILLEGAL_ARGUMENT'
        }

        where:
        requestDto                                                                                                            | testCase
        new DrugApplicationStoreDto(null, ['P001'].toSet(), ['S001'].toSet(), ['M1'].toSet())                                 | ' applicationNumber is null'
        new DrugApplicationStoreDto('', ['P001'].toSet(), ['S001'].toSet(), ['M1'].toSet())                                   | ' applicationNumber is empty'
        new DrugApplicationStoreDto(randomAlphabetic(257), ['P001'].toSet(), ['S001'].toSet(), ['M1'].toSet())                | ' applicationNumber is too long'
        new DrugApplicationStoreDto(randomAlphabetic(3), [randomAlphabetic(257)].toSet(), ['S001'].toSet(), ['M1'].toSet())   | ' productNumber is too long'
        new DrugApplicationStoreDto(randomAlphabetic(3), ['P001'].toSet(), [randomAlphabetic(257)].toSet(), ['M1'].toSet())   | ' substanceName is too long'
        new DrugApplicationStoreDto(randomAlphabetic(3), ['P001'].toSet(), ['S001'].toSet(), [randomAlphabetic(257)].toSet()) | ' manufacturerName is too long'
    }

    def "user with invalid auth token should not store new drug application"() {
        given:
        def requestDto = new DrugApplicationStoreDto(
                applicationNumber: applicationNumber,
                productNumbers: ['P001', 'P002'],
                substanceNames: ['S001', 'S002'],
                manufacturerNames: ['M1', 'M2']
        )

        and:
        assert drugApplicationRepository.findByApplicationNumber(applicationNumber).isEmpty()

        when:
        def storeApplicationResponse = put("/drugApplication", requestDto, Object, getHeadersWitInvalidAuthToken())

        then:
        assertResponseStatus(storeApplicationResponse, UNAUTHORIZED)
    }

    def "should store new drug application"() {
        given:
        def requestDto = new DrugApplicationStoreDto(
                applicationNumber: applicationNumber,
                productNumbers: ['P001', 'P002'],
                substanceNames: ['S001', 'S002'],
                manufacturerNames: ['M1', 'M2']
        )

        and:
        assert drugApplicationRepository.findByApplicationNumber(applicationNumber).isEmpty()

        when:
        def storeApplicationResponse = put("/drugApplication", requestDto)
        def newStoredDrugApplication = drugApplicationRepository.findByApplicationNumber(applicationNumber).orElse(null)

        then:
        assertResponseStatus(storeApplicationResponse)

        verifyAll {
            newStoredDrugApplication
            newStoredDrugApplication.applicationNumber == applicationNumber
            newStoredDrugApplication.substanceNames.sort() == requestDto.substanceNames.sort()
            newStoredDrugApplication.manufacturerNames.sort() == requestDto.manufacturerNames.sort()
            newStoredDrugApplication.productNumbers.sort() == requestDto.productNumbers.sort()
        }
    }

    def "should update existing stored drug application"() {
        given:
        def requestDto = new DrugApplicationStoreDto(
                applicationNumber: applicationNumber,
                productNumbers: ['P003', 'P002'],
                substanceNames: ['S001', 'S003'],
                manufacturerNames: ['M1', 'M2', 'M3']
        )

        and:
        assert drugApplicationRepository.findByApplicationNumber(applicationNumber).isPresent()

        when:
        def storeApplicationResponse = put("/drugApplication", requestDto)
        def storedDrugApplication = drugApplicationRepository.findByApplicationNumber(applicationNumber).orElse(null)

        then:
        assertResponseStatus(storeApplicationResponse)

        verifyAll {
            storedDrugApplication
            storedDrugApplication.applicationNumber == applicationNumber
            storedDrugApplication.substanceNames.sort() == requestDto.substanceNames.sort()
            storedDrugApplication.manufacturerNames.sort() == requestDto.manufacturerNames.sort()
            storedDrugApplication.productNumbers.sort() == requestDto.productNumbers.sort()
        }
    }

    def "should not search with invalid params :#testCase"() {
        when:
        def searchResponse = get("/drugApplication/search?$queryParams")

        then:
        assertResponseStatus(searchResponse, BAD_REQUEST)

        where:
        queryParams                      | testCase
        'size=0'                         | 'page size to small'
        'size=1001'                      | 'page size to big'
        'page=-1'                        | 'page size to small'
        'orderBy=invalidOrderByProperty' | 'orderBy invalid'
        'orderDir=invalidSortBy'         | 'ascending'
    }

    def "user with invalid auth token should not search"() {
        given:
        def queryParams = 'size=10&' +
                'page=0&'

        when:
        def searchResponse = get("/drugApplication/search?$queryParams", Object, getHeadersWitInvalidAuthToken())

        then:
        assertResponseStatus(searchResponse, UNAUTHORIZED)
    }

    def "should search by given params"() {
        given:
        def queryParams = 'size=10&' +
                'page=0&' +
                'orderBy=applicationNumber&' +
                'orderDir=asc&' +
                'applicationNumber=bLa&' +
                'productNumber=002&' +
                'manufacturerName=sAnofi&' +
                'substanceName=saril'

        when:
        def searchResponse = get("/drugApplication/search?$queryParams")

        then:
        assertResponseStatus(searchResponse)

        verifyAll(searchResponse.body) {
            total == 1
            size == 10
            totalPages == 1
            data.size() == 1
            data.first().applicationNumber == 'BLA761037'
            data.first().manufacturerNames == ['sanofi-aventis U.S. LLC']
            data.first().substanceNames == ['SARILUMAB']
            data.first().productNumbers == ['001', '002']
        }
    }

    def "user with invalid auth token should not get drug application"() {
        when:
        def searchResponse = get("/drugApplication", Object, getHeadersWitInvalidAuthToken())

        then:
        assertResponseStatus(searchResponse, UNAUTHORIZED)
    }

    def "should not get drug application for applicationNumber :#applicationNumber"() {
        when:
        def param = applicationNumber != null ? "applicationNumber=$applicationNumber" : ''
        def response = get("/drugApplication?$param")

        then:
        assertResponseStatus(response, BAD_REQUEST)

        verifyAll {
            response.body.errorCode == 'ILLEGAL_ARGUMENT'
        }

        where:
        applicationNumber << ['', null]
    }

    def "should not get not existing drug application"() {
        when:
        def response = get("/drugApplication?applicationNumber=not_exist")


        then:
        assertResponseStatus(response, NOT_FOUND)

        verifyAll {
            response.body.errorCode == 'NOT_FOUND'
        }
    }

    def "should get drug application"() {
        given:
        def appNumber = 'BLA761037'

        when:
        def response = get("/drugApplication?applicationNumber=$appNumber")

        then:
        assertResponseStatus(response)

        verifyAll {
            response.body.applicationNumber == 'BLA761037'
            response.body.manufacturerNames == ['sanofi-aventis U.S. LLC']
            response.body.substanceNames == ['SARILUMAB']
            response.body.productNumbers == ['001', '002']
        }
    }
}