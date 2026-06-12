package io.github.aughtone.gravatar

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GravatarApiTest {

    private val testHash = "99511d6010af8c574c31f94e1b327bba5e25086dd7b92a4b6f3e132b579cc8d1"

    private val profileJson = """
        {
          "hash": "$testHash",
          "display_name": "Example",
          "profile_url": "https://gravatar.com/examplefork",
          "avatar_url": "https://0.gravatar.com/avatar/$testHash",
          "avatar_alt_text": "User's avatar",
          "location": "E.G.",
          "description": "Sorry, this is not my name.",
          "pronouns": "He/Him",
          "job_title": "Chief",
          "company": "EG Inc",
          "verified_accounts": [
            {
              "service": "github",
              "url": "https://github.com/example",
              "username": "example",
              "verified": true,
              "service_type": "github",
              "service_label": "GitHub",
              "service_icon": "https://secure.gravatar.com/services/github.png",
              "is_hidden": false
            }
          ],
          "pronunciation": "ex-am-ple",
          "timezone": "America/New_York",
          "languages": ["en", "fr"],
          "first_name": "John",
          "last_name": "Doe",
          "is_organization": false,
          "header_image": "https://secure.gravatar.com/header.jpg",
          "hide_default_header_image": true,
          "background_color": "#ffffff",
          "links": [
            {
              "label": "My Web",
              "url": "https://example.com"
            }
          ],
          "interests": [
            {
              "id": "123",
              "name": "Kotlin",
              "slug": "123-kotlin"
            }
          ],
          "payments": {
            "links": [],
            "crypto_wallets": [
              {
                "label": "Bitcoin",
                "address": "1abc..."
              }
            ]
          },
          "contact_info": {
            "phone": "+123456789",
            "email": "example@example.com"
          },
          "gallery": [
            {
              "image_id": "img123",
              "image_url": "https://0.gravatar.com/avatar/img123",
              "rating": "g",
              "alt_text": "Alt text",
              "selected": true,
              "updated_date": "2026-01-01"
            }
          ],
          "number_verified_accounts": 1,
          "last_profile_edit": "2026-06-12",
          "registration_date": "2020-01-01"
        }
    """.trimIndent()

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testGetProfileParsesAllV3Fields() = runTest {
        val mockEngine = MockEngine { request ->
            assertEquals("https://api.gravatar.com/v3/profiles/$testHash", request.url.toString())
            respond(
                content = profileJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val mockClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
        }

        val api = GravatarApi(apiKey = "mock_key", client = mockClient)
        val result = api.getProfile(emailOrHash = testHash)
        
        assertTrue(result.isSuccess)
        val profile = result.getOrThrow()
        
        assertEquals(testHash, profile.hash)
        assertEquals("Example", profile.displayName)
        assertEquals("User's avatar", profile.avatarAltText)
        assertEquals("He/Him", profile.pronouns)
        assertEquals("ex-am-ple", profile.pronunciation)
        assertEquals("America/New_York", profile.timezone)
        assertEquals(listOf("en", "fr"), profile.languages)
        assertEquals("John", profile.firstName)
        assertEquals("Doe", profile.lastName)
        assertEquals(false, profile.isOrganization)
        assertEquals("https://secure.gravatar.com/header.jpg", profile.headerImage)
        assertEquals(true, profile.hideDefaultHeaderImage)
        assertEquals("#ffffff", profile.backgroundColor)
        
        // Links
        assertNotNull(profile.links)
        assertEquals(1, profile.links.size)
        assertEquals("My Web", profile.links[0].label)
        assertEquals("https://example.com", profile.links[0].url)

        // Interests
        assertNotNull(profile.interests)
        assertEquals(1, profile.interests.size)
        assertEquals("123", profile.interests[0].id)
        assertEquals("Kotlin", profile.interests[0].name)
        assertEquals("123-kotlin", profile.interests[0].slug)

        // Payments
        assertNotNull(profile.payments)
        val wallets = profile.payments.cryptoWallets
        assertNotNull(wallets)
        assertEquals(1, wallets.size)
        assertEquals("Bitcoin", wallets[0].label)
        assertEquals("1abc...", wallets[0].address)

        // Contact Info
        assertNotNull(profile.contactInfo)
        assertEquals("+123456789", profile.contactInfo.phone)
        assertEquals("example@example.com", profile.contactInfo.email)

        // Gallery
        assertNotNull(profile.gallery)
        assertEquals(1, profile.gallery.size)
        assertEquals("img123", profile.gallery[0].imageId)

        // Verified Accounts
        assertNotNull(profile.verifiedAccounts)
        assertEquals(1, profile.verifiedAccounts.size)
        val acc = profile.verifiedAccounts[0]
        assertEquals("github", acc.service)
        assertEquals("github", acc.serviceType)
        assertEquals("GitHub", acc.serviceLabel)
        assertEquals("https://secure.gravatar.com/services/github.png", acc.serviceIcon)
        assertEquals(false, acc.isHidden)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testGetQrCodeDownloadsBytes() = runTest {
        val mockQrBytes = byteArrayOf(1, 2, 3, 4, 5)
        val mockEngine = MockEngine { request ->
            assertEquals("https://api.gravatar.com/v3/qr-code/$testHash?size=300", request.url.toString())
            respond(
                content = mockQrBytes,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "image/png")
            )
        }

        val mockClient = HttpClient(mockEngine)

        val api = GravatarApi(apiKey = "mock_key", client = mockClient)
        val result = api.getQrCode(emailOrHash = testHash, size = 300)
        
        assertTrue(result.isSuccess)
        val bytes = result.getOrThrow()
        assertEquals(5, bytes.size)
        assertEquals(1, bytes[0])
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testMeEndpoints() = runTest {
        val mockEngine = MockEngine { request ->
            when (request.url.toString()) {
                "https://api.gravatar.com/v3/me/profile" -> {
                    assertEquals(HttpMethod.Patch, request.method)
                    respond(
                        content = """{"hash": "$testHash", "display_name": "Updated Name"}""",
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
                "https://api.gravatar.com/v3/me/avatars" -> {
                    assertEquals(HttpMethod.Get, request.method)
                    respond(
                        content = """[{"image_id": "avatar123", "image_url": "https://secure.gravatar.com/avatar123.jpg", "rating": "g", "alt_text": "", "selected": true, "updated_date": ""}]""",
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
                "https://api.gravatar.com/v3/me/avatars/avatar123/email" -> {
                    assertEquals(HttpMethod.Post, request.method)
                    respond(
                        content = "",
                        status = HttpStatusCode.OK
                    )
                }
                else -> respond(content = "", status = HttpStatusCode.NotFound)
            }
        }

        val mockClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
        }

        val api = GravatarApi(apiKey = "mock_key", client = mockClient)

        // Test updateProfile
        val updateResult = api.updateProfile(request = UpdateProfileRequest(displayName = "Updated Name"))
        assertTrue(updateResult.isSuccess)
        assertEquals("Updated Name", updateResult.getOrThrow().displayName)

        // Test retrieveAvatars
        val avatarsResult = api.retrieveAvatars()
        assertTrue(avatarsResult.isSuccess)
        assertEquals(1, avatarsResult.getOrThrow().size)
        assertEquals("avatar123", avatarsResult.getOrThrow()[0].imageId)

        // Test activateAvatar
        val activateResult = api.activateAvatar(imageId = "avatar123", emails = listOf("test@example.com"))
        assertTrue(activateResult.isSuccess)
    }
}
