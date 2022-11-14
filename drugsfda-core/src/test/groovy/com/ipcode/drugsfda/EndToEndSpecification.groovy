package com.ipcode.drugsfda


import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.ipcode.drugsfda.infrastructure.spring.ApplicationSecurityProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import static org.springframework.http.HttpMethod.PUT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.MediaType.APPLICATION_JSON

@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EndToEndSpecification extends DatabaseIntegrationSpecification {

    @Autowired
    ApplicationSecurityProperties applicationSecurityProperties

    def restTemplate = new TestRestTemplate()

    @Value('${local.server.port}')
    int port

    @Value('${server.servlet.context-path}')
    def contextPath

    @Value('${application.fda.api.keyValue}')
    String fdaApiKeyValue

    @Autowired
    WireMockServer wireMockServer

    def wireMockGetFdaApi(String responseBodyJson, HttpStatus status = OK) {
        wireMockServer.stubFor(
                WireMock.get(urlMatching("/drug/drugsfda.json?(.*)"))
                        .withQueryParam('api_key', equalTo(fdaApiKeyValue))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withStatus(status.value())
                                .withBody(responseBodyJson))
        )
    }

    def url() {
        "http://localhost:${port}${contextPath}"
    }

    def getCommonHeaders() {
        def headers = new HttpHeaders()
        headers.setContentType(APPLICATION_JSON)
        headers.setBearerAuth(applicationSecurityProperties.getApiKeyToken())
        headers
    }

    def getHeadersWitInvalidAuthToken() {
        def headers = new HttpHeaders()
        headers.setContentType(APPLICATION_JSON)
        headers.setBearerAuth("invalid_token_value")
        headers
    }

    def <T> ResponseEntity<T> get(String path) {
        get(path, Object, getCommonHeaders())
    }

    def <T> ResponseEntity<T> get(String path, Class<T> responseType, HttpHeaders headers) {
        def responseEntity = restTemplate.exchange(url() + path, HttpMethod.GET, new HttpEntity<Object>(headers),
                responseType)

        responseEntity
    }

    def <T> ResponseEntity<T> post(String path, def body) {
        post(path, body, Object, getCommonHeaders())
    }

    def <T> ResponseEntity<T> post(String path, def body, Class<T> returnType, HttpHeaders headers) {
        def responseEntity = restTemplate.postForEntity(url() + path, new HttpEntity<>(body, headers), returnType)

        responseEntity
    }

    def <T> ResponseEntity<T> put(String path, def body) {
        put(path, body, Object, getCommonHeaders())
    }

    def <T> ResponseEntity<T> put(String path, def body, Class<T> returnType = Void, HttpHeaders headers) {
        def responseEntity = restTemplate.exchange(url() + path, PUT, new HttpEntity<>(body, headers), returnType) as ResponseEntity<T>
        responseEntity
    }

    def assertResponseStatus(def result, status = OK) {
        verifyAll {
            result.statusCode == status
        }
        true
    }

}
