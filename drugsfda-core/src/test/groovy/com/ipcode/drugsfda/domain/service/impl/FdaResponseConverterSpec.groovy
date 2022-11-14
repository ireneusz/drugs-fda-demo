package com.ipcode.drugsfda.domain.service.impl

import com.ipcode.drugsfda.UnitSpecification
import com.ipcode.drugsfda.infrastructure.client.fda.FdaResponseUtil
import com.ipcode.drugsfda.infrastructure.client.fda.dto.DrugsFdaSearchResponse
import com.ipcode.drugsfda.util.FdaResponses

class FdaResponseConverterSpec extends UnitSpecification {

    def converter = new FdaResponseConverter()

    def "should convert fda response"() {
        given:
        def fdaResponse = FdaResponseUtil.getObjectFromJson(FdaResponses.fdaCorrectResponse, DrugsFdaSearchResponse)

        when:
        def conversionResult = converter.convert(fdaResponse)

        then:
        verifyAll(conversionResult) {
            total == fdaResponse.meta.results.total
            size == fdaResponse.meta.results.limit
            totalPages == 16
            data.applicationNumber == ['ANDA075880']
            data.manufacturerNames == [['Baxter Healthcare Corporation']]
            data.productNumbers == [['002', '001']]
            data.substanceNames == [['ALANINE', 'ARGININE']]
        }
    }

    def "execNo #execNo should return empty  searchResultPage"() {
        when:
        def conversionResult = converter.convert(fdaResponse)

        then:
        verifyAll {
            conversionResult
            conversionResult.totalPages == 0
            conversionResult.total == 0
            conversionResult.size == 0
            conversionResult.data.size() == 0
        }

        where:
        execNo | fdaResponse
        '1'    | new DrugsFdaSearchResponse(results: [new DrugsFdaSearchResponse.Result()])
        '2'    | new DrugsFdaSearchResponse(meta: new DrugsFdaSearchResponse.Meta())
        '3'    | new DrugsFdaSearchResponse(meta: new DrugsFdaSearchResponse.Meta())
        '4'    | new DrugsFdaSearchResponse(meta: new DrugsFdaSearchResponse.Meta(results: new DrugsFdaSearchResponse.MetaResults()))
        '4'    | new DrugsFdaSearchResponse(meta: new DrugsFdaSearchResponse.Meta(results: new DrugsFdaSearchResponse.MetaResults()))

    }

}
