package com.ipcode.drugsfda.interfaces.rest.search

import com.ipcode.drugsfda.EndToEndSpecification
import com.ipcode.drugsfda.util.FdaResponses

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.UNAUTHORIZED

//@ActiveProfiles(["test", "fda-client-mock"])
class FdaSearchControllerIntegrationSpec extends EndToEndSpecification {

    def "should not search with invalid params :#testCase"() {
        when:
        def searchResponse = get("/fda/search?$queryParams")

        then:
        assertResponseStatus(searchResponse, BAD_REQUEST)

        where:
        queryParams                                         | testCase
        'size=0&manufacturerName=a'                         | 'page size to small'
        'size=1001&manufacturerName=a'                      | 'page size to big'
        'page=-1&manufacturerName=a'                        | 'page size to small'
        'orderBy=invalidOrderByProperty&manufacturerName=a' | 'orderBy invalid'
        'orderDir=invalidSortBy&manufacturerName=a'         | 'sort dir invalid'
        'manufacturerName='                                 | 'manufacturerName empty'
    }

    def "user with invalid auth token cannot search"() {
        given:
        def queryParams = 'size=10&' +
                'page=0&' +
                'orderBy=applicationNumber&' +
                'orderDir=asc&' +
                'manufacturerName=Baxter&'

        when:
        def searchResponse = get("/fda/search?$queryParams", Object, getHeadersWitInvalidAuthToken())

        then:
        assertResponseStatus(searchResponse, UNAUTHORIZED)
    }

    def "should search by given params"() {
        given:
        def queryParams = 'size=10&' +
                'page=0&' +
                'orderBy=applicationNumber&' +
                'orderDir=asc&' +
                'manufacturerName=Baxter&'

        and:
        stubFdaApiCorrectResponse()

        when:
        def searchResponse = get("/fda/search?$queryParams")

        then:
        assertResponseStatus(searchResponse)

        verifyAll(searchResponse.body) {
            total == 16
            size == 1
            totalPages == 16
            data.size() == 1
            data.first().applicationNumber == 'ANDA075880'
            data.first().manufacturerNames == ['Baxter Healthcare Corporation']
            data.first().substanceNames == ['ALANINE', 'ARGININE']
            data.first().productNumbers == ['002', '001']
        }
    }

    def "should return empty page when not found matches"() {
        given:
        def queryParams = 'size=10&' +
                'page=0&' +
                'orderBy=applicationNumber&' +
                'orderDir=asc&' +
                'manufacturerName=Baxter&'

        and:
        stubFdaApiNotFoundMatchesResponse()

        when:
        def searchResponse = get("/fda/search?$queryParams")

        then:
        assertResponseStatus(searchResponse)

        verifyAll(searchResponse.body) {
            total == 0
            size == 0
            totalPages == 0
            data.size() == 0
        }
    }

    def "should return correct response for fda bad request response"() {
        given:
        def queryParams = 'size=10&' +
                'page=0&' +
                'orderBy=applicationNumber&' +
                'orderDir=asc&' +
                'manufacturerName=Baxter&'

        and:
        stubFdaApiBadRequest()

        when:
        def searchResponse = get("/fda/search?$queryParams")

        then:
        assertResponseStatus(searchResponse, INTERNAL_SERVER_ERROR)

        verifyAll(searchResponse.body) {
            errorCode == 'FDA_API_ERROR_400'
            errorMessage == 'Limit cannot exceed 1000 results for search requests. Use the skip or search_after param to get additional results.'
        }
    }

    def "should return correct response for fda server error response"() {
        given:
        def queryParams = 'size=10&' +
                'page=0&' +
                'orderBy=applicationNumber&' +
                'orderDir=asc&' +
                'manufacturerName=Baxter&'

        and:
        stubFdaApiServerErrorRequest()

        when:
        def searchResponse = get("/fda/search?$queryParams")

        then:
        assertResponseStatus(searchResponse, INTERNAL_SERVER_ERROR)

        verifyAll(searchResponse.body) {
            errorCode == 'FDA_API_ERROR_500'
            errorMessage == 'Drugs & Rock & Roll'
        }
    }

    def stubFdaApiCorrectResponse() {
        wireMockGetFdaApi(FdaResponses.fdaCorrectResponse)
    }

    def stubFdaApiNotFoundMatchesResponse() {
        wireMockGetFdaApi(FdaResponses.fdaNotFoundMatches, NOT_FOUND)
    }

    def stubFdaApiBadRequest() {
        wireMockGetFdaApi(FdaResponses.fdaBadRequest, BAD_REQUEST)
    }

    def stubFdaApiServerErrorRequest() {
        wireMockGetFdaApi(FdaResponses.fdaSeverError, INTERNAL_SERVER_ERROR)
    }
}
