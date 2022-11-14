package com.ipcode.drugsfda.domain.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.apache.commons.lang3.StringUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

import java.util.stream.Collectors

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull

class DrugApplicationRepositoryInMemory implements DrugApplicationRepository {

    private Map<String, DrugApplicationStub> db = [:]

    @Override
    DrugApplication newInstance(String applicationNumber) {
        def app = new DrugApplicationStub()
        app.setApplicationNumber(applicationNumber)

        app
    }

    @Override
    void store(DrugApplication drugApplication) {
        db.put(drugApplication.getApplicationNumber(), drugApplication)
    }

    @Override
    Optional<DrugApplication> findByApplicationNumber(String applicationNumber) {
        Optional.ofNullable(db.getOrDefault(applicationNumber, null))
    }

    @Override
    Page<DrugApplication> search(List<SearchCriteria> searchCriteriaList, Pageable pageable) {
        Map<String, String> criteriaMap = searchCriteriaList.stream()
                .filter(it -> it.getValue() != null)
                .collect(Collectors.toMap(SearchCriteria::getKey, it -> (String) it.getValue()))

        def result = db.values().stream().filter(
                it ->
                        criteriaMap.containsKey("applicationNumber") ? StringUtils.containsIgnoreCase(it.getApplicationNumber(),
                                criteriaMap.get("applicationNumber")) : true
        ).collect(Collectors.toList())

        new PageImpl<DrugApplication>(result)
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class DrugApplicationStub implements DrugApplication {
        String applicationNumber
        List<String> manufacturerNames = []
        List<String> productNumbers = []
        List<String> substanceNames = []

        @Override
        void update(Set<String> manufacturerNames, Set<String> substanceNames, Set<String> productNumbers) {
            this.manufacturerNames.clear()
            this.productNumbers.clear()
            this.substanceNames.clear()

            emptyIfNull(manufacturerNames).stream()
                    .forEach(this.manufacturerNames::add)
            emptyIfNull(substanceNames).stream()
                    .forEach(this.substanceNames::add)
            emptyIfNull(productNumbers).stream()
                    .forEach(this.productNumbers::add)
        }
    }
}
