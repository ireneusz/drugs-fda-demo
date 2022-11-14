package com.ipcode.drugsfda.util

import com.ipcode.drugsfda.infrastructure.client.fda.FdaClientMock

class FdaResponses {

    static fdaCorrectResponse = FdaClientMock.TEST_RESPONSE_BODY

    static fdaNotFoundMatches = """
{
  "error": {
    "code": "NOT_FOUND",
    "message": "No matches found!"
  }
}"""

    static fdaBadRequest = """
{
  "error": {
    "code": "BAD_REQUEST",
    "message": "Limit cannot exceed 1000 results for search requests. Use the skip or search_after param to get additional results."
  }
}"""


    static fdaSeverError = """
{
  "error": {
    "code": "INTERNAL_SERVER_ERROR",
    "message": "Drugs & Rock & Roll"
  }
}"""

}