package com.ipcode.drugsfda.infrastructure.client.fda

import com.ipcode.drugsfda.UnitSpecification
import com.ipcode.drugsfda.infrastructure.client.fda.dto.DrugsFdaSearchResponse
import com.ipcode.drugsfda.util.FdaResponses

class FdaResponseUtilSpec extends UnitSpecification {

    def "should return correct object"() {
        when:
        def result = FdaResponseUtil.getObjectFromJson(FdaResponses.fdaCorrectResponse, DrugsFdaSearchResponse)

        then:
        noExceptionThrown()
        verifyAll(result) {
            results.applicationNumber == ['ANDA075880']
        }
    }

    def "should return null when json is incorect"() {
        when:
        def result = FdaResponseUtil.getObjectFromJson('{"ss,}', DrugsFdaSearchResponse)

        then:
        noExceptionThrown()
        result == null
    }

}
