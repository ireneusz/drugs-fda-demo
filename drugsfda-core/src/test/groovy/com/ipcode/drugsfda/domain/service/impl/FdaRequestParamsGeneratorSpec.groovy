package com.ipcode.drugsfda.domain.service.impl

import com.ipcode.drugsfda.UnitSpecification
import com.ipcode.drugsfda.domain.shared.Pagination
import com.ipcode.drugsfda.domain.shared.search.FdaSearchOrderByField

import static com.ipcode.drugsfda.domain.shared.search.FdaSearchCriteria.builder

class FdaRequestParamsGeneratorSpec extends UnitSpecification {

    def generator = new FdaRequestParamsGenerator()

    def "execNo:#execNo should return correct search param string for given search criteria"() {
        when:
        def result = generator.getSearchParam(searhCriteria)

        then:
        result == expectedSearchValue

        where:
        execNo | searhCriteria                                              | expectedSearchValue
        '1'    | builder().build()                                          | ''
        '2'    | builder().manufacturerName('san').build()                  | 'openfda.manufacturer_name:"san"'
        '3'    | builder().brandName('brd').build()                         | 'openfda.brand_name:"brd"'
        '4'    | builder().manufacturerName('san').brandName('brd').build() | 'openfda.manufacturer_name:"san",openfda.brand_name:"brd"'
    }

    def "should return correct skip value for given pagination"() {
        given:
        def pagination = new Pagination<>(3, 10, FdaSearchOrderByField.applicationNumber, Pagination.SortDir.asc)

        when:
        def result = generator.getSkipValue(pagination)

        then:
        result == 30
    }

    def "should return correct sort value for given sortDir #sortDir"() {
        given:
        def pagination = new Pagination<>(3, 10, FdaSearchOrderByField.applicationNumber, sortDir)

        when:
        def result = generator.getSortValue(pagination)

        then:
        result == expectedValue

        where:
        sortDir                 | expectedValue
        Pagination.SortDir.asc  | 'application_number:asc'
        Pagination.SortDir.desc | 'application_number:desc'
    }
}
