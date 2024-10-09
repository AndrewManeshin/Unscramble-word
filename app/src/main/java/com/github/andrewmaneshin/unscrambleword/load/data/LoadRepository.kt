package com.github.andrewmaneshin.unscrambleword.load.data

import com.github.andrewmaneshin.unscrambleword.load.data.cache.WordCache
import com.github.andrewmaneshin.unscrambleword.load.data.cache.WordsDao
import com.github.andrewmaneshin.unscrambleword.load.data.cloud.LoadResult
import com.github.andrewmaneshin.unscrambleword.load.data.cloud.WordService
import kotlinx.coroutines.delay

interface LoadRepository {

    suspend fun load(size: Int): LoadResult

    class Base(
        private val service: WordService,
        private val dao: WordsDao
    ) : LoadRepository {

        override suspend fun load(size: Int): LoadResult {
            try {
                val result = service.words(size).execute()
                if (result.isSuccessful) {
                    val body = result.body()!!
                    if (body.status in 200..299) {
                        val list = body.wordsList
                        if (list.isEmpty()) {
                            return LoadResult.Error("Empty data, try again later")
                        } else {
                            dao.saveWords(body.wordsList.mapIndexed { index, word ->
                                WordCache(id = index, word = word)
                            })
                            return LoadResult.Success
                        }
                    } else {
                        return LoadResult.Error(handleResponseCode(result.body()!!.status))
                    }
                } else {
                    return LoadResult.Error(handleResponseCode(result.body()!!.status))
                }
            } catch (e: Exception) {
                return (LoadResult.Error(e.message ?: "error"))
            }
        }

        private fun handleResponseCode(code: Int): String {
            return when (code) {
                // Client Error
                400 -> "400 Bad Request: The server could not understand the request due to invalid syntax."
                401 -> "401 Unauthorized: The client must authenticate itself to get the requested response."
                402 -> "402 Payment Required: Reserved for future use."
                403 -> "403 Forbidden: The client does not have access rights to the content."
                404 -> "404 Not Found: The server can not find the requested resource."
                405 -> "405 Method Not Allowed: The method specified in the request is not allowed for the resource identified by the request URI."
                406 -> "406 Not Acceptable: The resource identified by the request is only capable of generating response entities which have content characteristics not acceptable according to the accept headers sent in the request."
                407 -> "407 Proxy Authentication Required: The client must first authenticate itself with the proxy."
                408 -> "408 Request Timeout: The server timed out waiting for the request."
                409 -> "409 Conflict: The request could not be completed due to a conflict with the current state of the resource."
                410 -> "410 Gone: The requested resource is no longer available at the server and no forwarding address is known."
                411 -> "411 Length Required: The server refuses to accept the request without a defined Content-Length."
                412 -> "412 Precondition Failed: The precondition given in one or more of the request-header fields evaluated to false when it was tested on the server."
                413 -> "413 Payload Too Large: The server is refusing to process a request because the request entity is larger than the server is willing or able to process."
                414 -> "414 URI Too Long: The server is refusing to service the request because the request-URI is longer than the server is willing to interpret."
                415 -> "415 Unsupported Media Type: The server is refusing to service the request because the entity of the request is in a format not supported by the requested resource for the requested method."
                416 -> "416 Range Not Satisfiable: The client has asked for a portion of the file, but the server cannot supply that portion."
                417 -> "417 Expectation Failed: The server cannot meet the requirements of the Expect request-header field."
                418 -> "418 I'm a teapot: The server refuses to brew coffee because it is, permanently, a teapot."
                421 -> "421 Misdirected Request: The request was directed at a server that is not able to produce a response."
                422 -> "422 Unprocessable Entity: The request was well-formed but was unable to be followed due to semantic errors."
                423 -> "423 Locked: The resource that is being accessed is locked."
                424 -> "424 Failed Dependency: The request failed due to failure of a previous request."
                425 -> "425 Too Early: Indicates that the server is unwilling to risk processing a request that might be replayed."
                426 -> "426 Upgrade Required: The server refuses to perform the request using the current protocol but might be willing to do so after the client upgrades to a different protocol."
                428 -> "428 Precondition Required: The origin server requires the request to be conditional."
                429 -> "429 Too Many Requests: The user has sent too many requests in a given amount of time."
                431 -> "431 Request Header Fields Too Large: The server is unwilling to process the request because its header fields are too large."
                451 -> "451 Unavailable For Legal Reasons: The user agent requested a resource that cannot legally be provided."

                // Server Error
                500 -> "500 Internal Server Error: The server has encountered a situation it doesn't know how to handle."
                501 -> "501 Not Implemented: The server does not support the functionality required to fulfill the request."
                502 -> "502 Bad Gateway: The server, while acting as a gateway or proxy, received an invalid response from the upstream server it accessed in attempting to fulfill the request."
                503 -> "503 Service Unavailable: The server is currently unable to handle the request due to a temporary overload or scheduled maintenance, which will likely be alleviated after some delay."
                504 -> "504 Gateway Timeout: The server, while acting as a gateway or proxy, did not receive a timely response from the upstream server it accessed in attempting to complete the request."
                505 -> "505 HTTP Version Not Supported: The server does not support the HTTP protocol version used in the request."
                506 -> "506 Variant Also Negotiates: The server has an internal configuration error: the chosen variant resource is configured to engage in transparent content negotiation itself, and is therefore not a proper end point in the negotiation process."
                507 -> "507 Insufficient Storage: The server is unable to store the representation needed to complete the request."
                508 -> "508 Loop Detected: The server detected an infinite loop while processing the request."
                510 -> "510 Not Extended: Further extensions to the request are required for the server to fulfill it."
                511 -> "511 Network Authentication Required: The client needs to authenticate to gain network access."

                else -> "Unknown Status Code"
            }
        }
    }

    class Fake : LoadRepository {

        private var count = 0

        override suspend fun load(size: Int): LoadResult {
            delay(2000)
            return if (count++ == 0)
                LoadResult.Error("")
            else
                LoadResult.Success
        }
    }
}