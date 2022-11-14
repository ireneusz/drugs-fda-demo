package com.ipcode.drugsfda.infrastructure.client.fda;

import com.ipcode.drugsfda.infrastructure.client.fda.dto.DrugsFdaSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("fda-client-mock")
public class FdaClientMock implements FdaClientWrapper {

	public static final String TEST_RESPONSE_BODY = """
			{
			  "meta": {
			    "disclaimer": "Do not rely on openFDA to make decisions regarding medical care. While we make every effort to ensure that data is accurate, you should assume all results are unvalidated. We may limit or otherwise restrict your access to the API in line with our Terms of Service.",
			    "terms": "https://open.fda.gov/terms/",
			    "license": "https://open.fda.gov/license/",
			    "last_updated": "2022-11-08",
			    "results": {
			      "skip": 0,
			      "limit": 1,
			      "total": 16
			    }
			  },
			  "results": [
			    {
			      "submissions": [
			        {
			          "submission_type": "SUPPL",
			          "submission_number": "6",
			          "submission_status": "AP",
			          "submission_status_date": "20060206",
			          "submission_class_code": "LABELING",
			          "submission_class_code_description": "Labeling"
			        },
			        {
			          "submission_type": "ORIG",
			          "submission_number": "1",
			          "submission_status": "AP",
			          "submission_status_date": "20030619"
			        },
			        {
			          "submission_type": "SUPPL",
			          "submission_number": "1",
			          "submission_status": "AP",
			          "submission_status_date": "20040823",
			          "submission_class_code": "LABELING",
			          "submission_class_code_description": "Labeling"
			        }
			      ],
			      "application_number": "ANDA075880",
			      "sponsor_name": "BAXTER HLTHCARE",
			      "openfda": {
			        "application_number": [
			          "ANDA075880"
			        ],
			        "brand_name": [
			          "PREMASOL - SULFITE-FREE (AMINO ACID)"
			        ],
			        "generic_name": [
			          "LEUCINE, LYSINE, ISOLEUCINE, VALINE, HISTIDINE, PHENYLALANINE, THREONINE, METHIONINE, TRYPTOPHAN, TYROSINE, N-ACETYL-TYROSINE, ARGININE, PROLINE, ALANINE, GLUTAMIC ACIDE, SERINE, GLYCINE, ASPARTIC ACID, TAURINE, CYSTEINE HYDROCHLORIDE"
			        ],
			        "manufacturer_name": [
			          "Baxter Healthcare Corporation"
			        ],
			        "product_ndc": [
			          "0338-1130",
			          "0338-1131"
			        ],
			        "product_type": [
			          "HUMAN PRESCRIPTION DRUG"
			        ],
			        "route": [
			          "INTRAVENOUS"
			        ],
			        "substance_name": [
			          "ALANINE",
			          "ARGININE"      
			        ],
			        "rxcui": [
			          "801395",
			          "801398",
			          "801403",
			          "801405"
			        ],
			        "spl_id": [
			          "d6b2fb08-39c0-4f89-9ff5-3435376f898f"
			        ],
			        "spl_set_id": [
			          "9afdcc3e-0d06-47f4-86ca-40da48b2b02b"
			        ],
			        "package_ndc": [
			          "0338-1130-03",
			          "0338-1130-04",
			          "0338-1130-06",
			          "0338-1131-03"
			        ],
			        "nui": [
			          "N0000175780",
			          "M0000922"
			        ],
			        "pharm_class_epc": [
			          "Amino Acid [EPC]"
			        ],
			        "pharm_class_cs": [
			          "Amino Acids [CS]"
			        ],
			        "unii": [
			          "OF5P57N2ZX",
			          "94ZLA3W45F",
			          "30KYC7MIAI",
			          "ZT934N0X4W",
			          "3KX376GY7L",
			          "TE7660XO1C",
			          "4QD397987E",
			          "04Y7590D77",
			          "GMW67QNF9C",
			          "TTL6G7LIWZ",
			          "AE28F7PNPL",
			          "47E5O17Y3R",
			          "9DLQ4CIU6V",
			          "452VLY9402",
			          "1EQV5MLY3D",
			          "2ZD004190S",
			          "8DUH1N11BX",
			          "42HK56048U",
			          "HG18B9YRS7"
			        ]
			      },
			      "products": [
			        {
			          "product_number": "002",
			          "reference_drug": "No",
			          "brand_name": "PREMASOL 10% IN PLASTIC CONTAINER",
			          "active_ingredients": [
			            {
			              "name": "AMINO ACIDS",
			              "strength": "10% (10GM/100ML)"
			            }
			          ],
			          "reference_standard": "No",
			          "dosage_form": "INJECTABLE",
			          "route": "INJECTION",
			          "marketing_status": "Prescription"
			        },
			        {
			          "product_number": "001",
			          "reference_drug": "No",
			          "brand_name": "PREMASOL 6% IN PLASTIC CONTAINER",
			          "active_ingredients": [
			            {
			              "name": "AMINO ACIDS",
			              "strength": "6% (6GM/100ML)"
			            }
			          ],
			          "reference_standard": "No",
			          "dosage_form": "INJECTABLE",
			          "route": "INJECTION",
			          "marketing_status": "Prescription"
			        }
			      ]
			    }
			  ]
			}""";

	@PostConstruct
	private void init() {
		log.warn("!!!!!!FdaClientMock enabled!!!!!");
	}

	@Override
	public DrugsFdaSearchResponse searchDrugsFda(Integer skip, Integer limit, String search, String sort) {
		return FdaResponseUtil.getObjectFromJson(TEST_RESPONSE_BODY, DrugsFdaSearchResponse.class);
	}
}
