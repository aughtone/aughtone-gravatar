package io.github.aughtone.gravatar

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.plugins.resources.get
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Gravatar API base URL - V3 */
internal const val GRAVATAR_API_BASE_URL_V3 = "https://api.gravatar.com/v3/"

@OptIn(ExperimentalStdlibApi::class)
class GravatarApi(
    var apiKey: String? = null,
    var appName: String? = null,
    private val client: HttpClient = HttpClient() {
        install(ContentNegotiation) {
            json()
        }
        install(Resources)

    },
) {
    suspend fun upload(
        oauthToken: String,
        email: String,
        selectAvatar: Boolean? = null,
        imageData: ByteArray,
    ): Result<Avatar> = runCatching {
        withContext(Dispatchers.Default) {
            val emailHash = Gravatar.requireHashed(email)
            throw RuntimeException("Not implemented")
        }
    }


    suspend fun retrieve(oauthToken: String, email: String): Result<List<Avatar>> =
        runCatching {
            withContext(Dispatchers.Default) {
                val emailHash = Gravatar.requireHashed(email)
            }
            throw RuntimeException("Not implemented")
//            return emptyList()
        }

    suspend fun setAvatar(
        oauthToken: String,
        email: String,
        avatarId: String,
    ): Result<Unit> =
        runCatching {
            withContext(Dispatchers.Default) {
                val emailHash = Gravatar.requireHashed(email)
                throw RuntimeException("Not implemented")
            }
        }

    suspend fun deleteAvatar(oauthToken: String, avatarId: String): Result<Unit> =
        runCatching {
            withContext(Dispatchers.Default) {
                throw RuntimeException("Not implemented")
            }
        }

    suspend fun updateAvatar(
        oauthToken: String,
        avatarId: String,
        avatarRating: Avatar.Rating? = null,
        altText: String? = null,
    ): Result<Nothing> = runCatching {
        withContext(Dispatchers.Default) {
            throw RuntimeException("Not implemented")
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    suspend fun getAvatarUrl(
        email: String,
        sizeInPixels: Int = 1024,
        defaultImage: String? = null,
    ): Result<String> =
        runCatching {
            require(sizeInPixels in 1..2048) { "The sizeInPixels parameter must be between 1 and 2048." }

            val emailHash = Gravatar.requireHashed(email)
            //        ?s=200
            val path = "avatar"

            val response = client.get(GRAVATAR_API_BASE_URL_V3) {
                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json.contentType)
//                append(HttpHeaders.Authorization, "abc123")
//                append(HttpHeaders.UserAgent, "ktor client")
                }

                url {
                    emailHash?.let {
                        appendPathSegments(segments = listOf(path, it))
                    }
                    parameters.append(
                        "s",
                        sizeInPixels.toString()
                    )
                    defaultImage?.let {
                        parameters.append(
                            "d",
                            it
                        )
                    }
                }
            }

            if (response.status.value in 200..299) {
                println("Successful response!")
                return Result.success("")
            } else if (response.status.value == 401) {
                return Result.failure(Exception("Unauthorized"))
            } else if (response.status.value == 403) {
                return Result.failure(Exception("Insufficient Scope"))
            } else {
                return Result.failure(Exception("Something went wrong"))
            }

            return Result.success(response.body())

        }
}
