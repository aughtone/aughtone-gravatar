package io.github.aughtone.gravatar

import kotlinx.coroutines.test.runTest
import java.io.File
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals

class GravatarApiLiveTest {

    @Test
    fun testLiveProfileRetrieval() = runTest {
        val secretsFile = File(System.getProperty("user.home") + "/.secrets/agents/aughtone-gravatar/gravatar_api_key.txt")
        if (!secretsFile.exists()) {
            println("Skipping live test: secrets file not found at ${secretsFile.absolutePath}")
            return@runTest
        }

        val apiKey = secretsFile.readText().trim()
        val api = GravatarApi(apiKey = apiKey)

        // Query public example profile using the API key
        val testHash = "99511d6010af8c574c31f94e1b327bba5e25086dd7b92a4b6f3e132b579cc8d1"
        val result = api.getProfile(emailOrHash = testHash)
        if (result.isFailure) {
            result.exceptionOrNull()?.printStackTrace()
        }
        assertTrue(result.isSuccess, "API call should succeed")
        
        val profile = result.getOrThrow()
        assertEquals(testHash, profile.hash)
        assertEquals("Example", profile.displayName)
        println("Successfully verified live API profile query! Display Name: ${profile.displayName}")
    }
}
