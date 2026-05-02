package io.github.aughtone.gravatar

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.*
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

/** Gravatar API base URL - V3 */
internal const val GRAVATAR_API_BASE_URL_V3 = "https://api.gravatar.com/v3/"

@OptIn(ExperimentalStdlibApi::class)
class GravatarApi(
    var apiKey: String? = null,
    var appName: String? = null,
    private val client: HttpClient = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
        install(Resources)
    },
) {

    private fun HttpRequestBuilder.auth(token: String?) {
        val finalToken = token ?: apiKey
        if (finalToken != null) {
            header(HttpHeaders.Authorization, "Bearer $finalToken")
        }
    }

    suspend fun uploadAvatar(
        oauthToken: String? = null,
        imageData: ByteArray,
        filename: String = "avatar.jpg",
        contentType: ContentType = ContentType.Image.JPEG,
        rating: Avatar.Rating? = null,
        altText: String? = null,
    ): Result<Avatar> = runCatching {
        client.submitFormWithBinaryData(
            url = "${GRAVATAR_API_BASE_URL_V3}avatars",
            formData = formData {
                append("file", imageData, Headers.build {
                    append(HttpHeaders.ContentType, contentType.toString())
                    append(HttpHeaders.ContentDisposition, "filename=\"$filename\"")
                })
                rating?.let { append("rating", it.name) }
                altText?.let { append("alt_text", it) }
            }
        ) {
            auth(oauthToken)
        }.body()
    }

    suspend fun retrieveAvatars(oauthToken: String? = null): Result<List<Avatar>> = runCatching {
        client.get("${GRAVATAR_API_BASE_URL_V3}avatars") {
            auth(oauthToken)
        }.body()
    }

    suspend fun activateAvatar(
        oauthToken: String? = null,
        imageId: String,
        emails: List<String>,
    ): Result<Unit> = runCatching {
        val hashes = emails.map { Gravatar.requireHashed(it) }
        val response = client.post("${GRAVATAR_API_BASE_URL_V3}avatars/activate") {
            auth(oauthToken)
            contentType(ContentType.Application.Json)
            setBody(ActivateAvatarRequest(imageId, hashes))
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Failed to activate avatar: ${response.status}")
        }
    }

    suspend fun deleteAvatar(oauthToken: String? = null, avatarId: String): Result<Unit> = runCatching {
        val response = client.delete("${GRAVATAR_API_BASE_URL_V3}avatars/$avatarId") {
            auth(oauthToken)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Failed to delete avatar: ${response.status}")
        }
    }

    suspend fun updateAvatar(
        oauthToken: String? = null,
        avatarId: String,
        rating: Avatar.Rating? = null,
        altText: String? = null,
    ): Result<Avatar> = runCatching {
        client.patch("${GRAVATAR_API_BASE_URL_V3}avatars/$avatarId") {
            auth(oauthToken)
            contentType(ContentType.Application.Json)
            setBody(UpdateAvatarRequest(rating, altText))
        }.body()
    }

    suspend fun getProfile(
        oauthToken: String? = null,
        emailOrHash: String,
    ): Result<Profile> = runCatching {
        val identifier = if (emailOrHash.contains("@")) Gravatar.requireHashed(emailOrHash) else emailOrHash
        client.get("${GRAVATAR_API_BASE_URL_V3}profiles/$identifier") {
            auth(oauthToken)
        }.body()
    }

    suspend fun updateProfile(
        oauthToken: String? = null,
        request: UpdateProfileRequest,
    ): Result<Profile> = runCatching {
        client.patch("${GRAVATAR_API_BASE_URL_V3}profiles") {
            auth(oauthToken)
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    // Deprecated or old methods kept for compatibility if needed, but updated to use v3
    @Deprecated("Use retrieveAvatars", ReplaceWith("retrieveAvatars(oauthToken)"))
    suspend fun retrieve(oauthToken: String, email: String): Result<List<Avatar>> = retrieveAvatars(oauthToken)

    @Deprecated("Use activateAvatar", ReplaceWith("activateAvatar(oauthToken, avatarId, listOf(email))"))
    suspend fun setAvatar(oauthToken: String, email: String, avatarId: String): Result<Unit> =
        activateAvatar(oauthToken, avatarId, listOf(email))
}
