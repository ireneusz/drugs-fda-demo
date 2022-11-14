package com.ipcode.drugsfda.application.impl

import com.ipcode.drugsfda.UnitSpecification
import com.ipcode.drugsfda.domain.model.DrugApplicationRepositoryInMemory
import spock.lang.Shared

class DrugApplicationInMemorySpec extends UnitSpecification {

    @Shared
    def drugApplicationRepository = new DrugApplicationRepositoryInMemory()

    @Shared
    def drugApplicationApplicationService = new DrugApplicationApplicationServiceImpl(drugApplicationRepository)

}
