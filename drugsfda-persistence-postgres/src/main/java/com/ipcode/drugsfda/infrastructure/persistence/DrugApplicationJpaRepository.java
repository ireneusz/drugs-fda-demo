package com.ipcode.drugsfda.infrastructure.persistence;

import com.ipcode.drugsfda.domain.model.DrugApplication;
import com.ipcode.drugsfda.domain.model.DrugApplicationJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface DrugApplicationJpaRepository extends JpaRepository<DrugApplicationJpa, String>, JpaSpecificationExecutor<DrugApplication> {

	Optional<DrugApplicationJpa> findByApplicationNumber(String applicationNumber);

}
