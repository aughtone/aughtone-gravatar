package io.github.aughtone.gravatar

import io.github.aughtone.gravatar.Gravatar.DefaultImage
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GravatarTest {
//    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val testHash = "21ba0fe27eb6ba49492e49beca5431f5f2f053640b41af189bf184edb8b26b62"
    private val testEmail = "brill@pappin.ca"
    private val gravatar = Gravatar

    //    @Before
//    fun setUp() {
//        Dispatchers.setMain(mainThreadSurrogate)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
//        mainThreadSurrogate.close()
//    }

//    @ExperimentalStdlibApi
//    @Test
//    fun testUrl() = runTest {
//        val actual = gravatar.getAvatarUrl(
//            email = "johnpappin@gmail.com",
//            sizeInPixels = 128,
//            defaultImage = DefaultImage.Initials(initials = "JP")
//        ).getOrThrow()
//        assertEquals("", actual)
//    }

    @ExperimentalStdlibApi
    @Test
    fun testRequireHashed() = runTest {
        val actual = gravatar.requireHashed(testEmail)
        assertEquals(testHash, actual)
    }

    @ExperimentalStdlibApi
    @Test
    fun testAvatarUrlWithNoParams() = runTest {
        val actual = gravatar.getAvatarUrl(email = testEmail).getOrThrow()
        assertEquals(
            "https://gravatar.com/avatar/$testHash?s=1024&r=g",
            actual
        )
    }

    @ExperimentalStdlibApi
    @Test
    fun testAvatarUrlWithAltSize() = runTest {
        val actual = gravatar.getAvatarUrl(email = testEmail, sizeInPixels = 100).getOrThrow()
        assertEquals(
            "https://gravatar.com/avatar/$testHash?s=100&r=g",
            actual
        )
    }

    @ExperimentalStdlibApi
    @Test
    fun testAvatarUrlWithForceDefault() = runTest {
        val actual =
            gravatar.getAvatarUrl(email = testEmail, sizeInPixels = 100, forceDefault = true)
                .getOrThrow()
        assertEquals(
            "https://gravatar.com/avatar/$testHash?s=100&r=g&f=true",
            actual
        )
    }

    @ExperimentalStdlibApi
    @Test
    fun testAvatarUrlWithDefaultImageColor() = runTest {
        val actual =
            gravatar.getAvatarUrl(email = testEmail, defaultImage = Gravatar.DefaultImage.Color)
                .getOrThrow()
        assertEquals(
            "https://gravatar.com/avatar/$testHash?s=1024&r=g&d=color",
            actual
        )
    }

    @ExperimentalStdlibApi
    @Test
    fun testAvatarUrlWithDefaultInitials() = runTest {
        val actual =
            gravatar.getAvatarUrl(
                email = testEmail,
                defaultImage = Gravatar.DefaultImage.Initials(initials = "JP")
            )
                .getOrThrow()
        assertEquals(
            "https://gravatar.com/avatar/$testHash?s=1024&r=g&d=initials&initials=JP",
            actual
        )
    }

    @ExperimentalStdlibApi
    @Test
    fun testAvatarUrlWithDefaultUsingName() = runTest {
        val actual =
            gravatar.getAvatarUrl(
                email = testEmail,
                defaultImage = Gravatar.DefaultImage.Initials(name = "John Pappin")
            )
                .getOrThrow()
        assertEquals(
            "https://gravatar.com/avatar/$testHash?s=1024&r=g&d=initials&name=John+Pappin",
            actual
        )
    }

    @ExperimentalStdlibApi
    @Test
    fun testAvatarUrlWithDefaultUsingSingleName() = runTest {
        val actual =
            gravatar.getAvatarUrl(
                email = testEmail,
                defaultImage = Gravatar.DefaultImage.Initials(name = "John")
            )
                .getOrThrow()
        assertEquals(
            "https://gravatar.com/avatar/$testHash?s=1024&r=g&d=initials&name=John",
            actual
        )
    }


}
