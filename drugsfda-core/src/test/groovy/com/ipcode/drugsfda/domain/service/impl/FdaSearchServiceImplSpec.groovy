package com.ipcode.drugsfda.domain.service.impl

import com.ipcode.drugsfda.UnitSpecification
import com.ipcode.drugsfda.domain.exception.FdaSystemNotFoundResultsException
import com.ipcode.drugsfda.domain.shared.Pagination
import com.ipcode.drugsfda.domain.shared.search.FdaSearchCriteria
import com.ipcode.drugsfda.domain.shared.search.FdaSearchOrderByField
import com.ipcode.drugsfda.infrastructure.client.fda.FdaClientWrapper
import com.ipcode.drugsfda.infrastructure.client.fda.FdaResponseUtil
import com.ipcode.drugsfda.infrastructure.client.fda.dto.DrugsFdaSearchResponse
import com.ipcode.drugsfda.util.FdaResponses
import org.springframework.http.HttpStatus

class FdaSearchServiceImplSpec extends UnitSpecification {

    def fdaClient = Mock(FdaClientWrapper)
    def fdaSearchService = new FdaSearchServiceImpl(fdaClient, new FdaRequestParamsGenerator(), new FdaResponseConverter())

    def "should return empty result page when  FdaSystemNotFoundResultsException thrown"() {
        given:
        def pagination = new Pagination<>(3, 10, FdaSearchOrderByField.applicationNumber, Pagination.SortDir.asc)
        def searchCriteria = FdaSearchCriteria.builder().manufacturerName('test').build()

        and:
        fdaClient.searchDrugsFda(_, _, _, _) >> {
            throw new FdaSystemNotFoundResultsException('no result found', HttpStatus.NOT_FOUND)
        }

        when:
        def searchResult = fdaSearchService.search(searchCriteria, pagination)

        then:
        verifyAll {
            searchResult.totalPages == 0
            searchResult.total == 0
            searchResult.data.size() == 0
        }
    }

    def "should return correct data"() {
        given:
        def pagination = new Pagination<>(3, 10, FdaSearchOrderByField.applicationNumber, Pagination.SortDir.desc)
        def searchCriteria = FdaSearchCriteria.builder().manufacturerName('test').build()
        def fdaResponse = FdaResponseUtil.getObjectFromJson(FdaResponses.fdaCorrectResponse, DrugsFdaSearchResponse)

        when:
        def searchResult = fdaSearchService.search(searchCriteria, pagination)

        then:
        verifyAll(searchResult) {
            total == fdaResponse.meta.results.total
            size == fdaResponse.meta.results.limit
            totalPages == 16
            data.applicationNumber == ['ANDA075880']
            data.manufacturerNames == [['Baxter Healthcare Corporation']]
            data.productNumbers == [['002', '001']]
            data.substanceNames == [['ALANINE', 'ARGININE']]
        }

        and:
        1 * fdaClient.searchDrugsFda(_, _, _, _) >> {
            def skip, def limit, def search, def sort ->

                verifyAll {
                    skip == 30
                    limit == 10
                    search == 'openfda.manufacturer_name:"test"'
                    sort == 'application_number:desc'
                }

                fdaResponse
        }
    }
}
