package com.ipcode.drugsfda

import org.flywaydb.test.annotation.FlywayTest
import org.springframework.transaction.annotation.Transactional

@FlywayTest
@Transactional
abstract class DatabaseIntegrationSpecification extends IntegrationSpecification {

}
