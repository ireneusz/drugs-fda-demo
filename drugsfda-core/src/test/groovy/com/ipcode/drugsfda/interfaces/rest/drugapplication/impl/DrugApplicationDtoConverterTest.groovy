package com.ipcode.drugsfda.interfaces.rest.drugapplication.impl

import com.ipcode.drugsfda.UnitSpecification
import com.ipcode.drugsfda.domain.model.DrugApplicationRepositoryInMemory
import org.springframework.data.domain.PageImpl


class DrugApplicationDtoConverterTest extends UnitSpecification {

    def converter = new DrugApplicationDtoConverter()

    def "should convert DrugApplication to dto"() {
        given:
        def drugApplication = drugApplication()
        when:
        def result = converter.convert(drugApplication)

        then:
        verifyAll {
            result.applicationNumber == drugApplication.getApplicationNumber()
            result.productNumbers == drugApplication.getProductNumbers()
            result.substanceNames == drugApplication.getSubstanceNames()
            result.manufacturerNames == drugApplication.getManufacturerNames()
        }
    }

    def "should convert DrugApplication page"() {
        given:
        def drugApplication = drugApplication()
        when:
        def result = converter.convertToPage(new PageImpl<>([drugApplication]))

        then:
        verifyAll {
            result.data.size() == 1
            result.size == 1
            result.total == 1
            result.totalPages == 1
            result.data.first().applicationNumber == drugApplication.getApplicationNumber()
            result.data.first().productNumbers == drugApplication.getProductNumbers()
            result.data.first().substanceNames == drugApplication.getSubstanceNames()
            result.data.first().manufacturerNames == drugApplication.getManufacturerNames()
        }
    }

    static drugApplication() {
        def da = new DrugApplicationRepositoryInMemory().newInstance('App1')
        da.update(['M1'].toSet(), ['S1'].toSet(), ['p2'].toSet())
        da
    }
}
