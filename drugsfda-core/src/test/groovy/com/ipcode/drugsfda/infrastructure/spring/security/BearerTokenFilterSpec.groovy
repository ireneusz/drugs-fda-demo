package com.ipcode.drugsfda.infrastructure.spring.security

import com.ipcode.drugsfda.UnitSpecification
import org.springframework.security.web.util.matcher.RequestMatcher

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static com.ipcode.drugsfda.infrastructure.spring.security.BearerTokenFilter.AUTHORIZATION_HEADER_NOT_FOUND_ERROR_MESSAGE
import static com.ipcode.drugsfda.infrastructure.spring.security.BearerTokenFilter.INVALID_AUTHORIZATION_HEADER_ERROR_MESSAGE
import static com.ipcode.drugsfda.infrastructure.spring.security.BearerTokenFilter.SECURITY_KEY_PREFIX
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED
import static org.springframework.http.HttpHeaders.AUTHORIZATION

class BearerTokenFilterSpec extends UnitSpecification {

    static securityApiKey = "dummy security api key"

    def requestMatcher = Mock(RequestMatcher)
    def request = Mock(HttpServletRequest)
    def response = Mock(HttpServletResponse)
    def chain = Mock(FilterChain)
    def filter = new BearerTokenFilter(securityApiKey, requestMatcher)

    def "should skip security api key verification if request url does not match filter match configuration"() {
        when:
        filter.doFilter(request, response, chain)

        then:
        1 * requestMatcher.matches(request) >> false
        1 * chain.doFilter(request, response)
        0 * request.getHeader(AUTHORIZATION)
    }

    def "should verify security api key (#requestSecurityApiKey) and return error (#expectedErrorMessage) if request url matched to filter match configuration and key is invalid"() {
        when:
        filter.doFilter(request, response, chain)

        then:
        1 * requestMatcher.matches(request) >> true
        1 * request.getHeader(AUTHORIZATION) >> requestSecurityApiKey
        1 * response.sendError(SC_UNAUTHORIZED, expectedErrorMessage)
        1 * request.getRemoteAddr() >> "dummy remote address"
        0 * chain.doFilter(request, response)

        where:
        requestSecurityApiKey | expectedErrorMessage
        null                  | AUTHORIZATION_HEADER_NOT_FOUND_ERROR_MESSAGE
        ''                    | INVALID_AUTHORIZATION_HEADER_ERROR_MESSAGE
        ' '                   | INVALID_AUTHORIZATION_HEADER_ERROR_MESSAGE
        'any string'          | INVALID_AUTHORIZATION_HEADER_ERROR_MESSAGE
        securityApiKey        | INVALID_AUTHORIZATION_HEADER_ERROR_MESSAGE
    }

    def "should verify security api key and pass request if request url matched to filter match configuration and key is valid"() {
        given:
        def givenRequestSecurityApiKey = SECURITY_KEY_PREFIX + securityApiKey

        when:
        filter.doFilter(request, response, chain)

        then:
        1 * requestMatcher.matches(request) >> true
        1 * request.getHeader(AUTHORIZATION) >> givenRequestSecurityApiKey
        1 * chain.doFilter(request, response)
        0 * response.sendError(_, _)
    }

}
