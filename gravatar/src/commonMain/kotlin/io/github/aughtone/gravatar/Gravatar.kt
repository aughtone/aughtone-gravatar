package io.github.aughtone.gravatar

import io.github.aughtone.gravatar.Avatar.Rating
import io.github.aughtone.types.uri.UrlEncoder
import org.kotlincrypto.hash.sha2.SHA256

object Gravatar {

    /**
     * Represents the default image to be displayed if no Gravatar is associated with the email hash.
     *
     * @property params The encoded representation of the default image for the Gravatar URL.
     */
    sealed class DefaultImage(val params: String) {
        data class Custom(val url: String) : DefaultImage(url)

        /** uses the profile name as initials, with a generated background and foreground color (beta). There are additional parameters, described later. **/
        data class Initials(val initials: String? = null, val name: String? = null) :
            DefaultImage(
                "initials${
                    initials?.let { "&initials=${UrlEncoder.encode(it)}" } ?: name?.let {
                        "&name=${
                            UrlEncoder.encode(
                                it
                            )
                        }"
                    } ?: ""
                }")

        /** : a generated color (beta) **/
        data object Color : DefaultImage("color")

        /** : do not load any image if none is associated with the email hash, instead return an HTTP 404 (File Not Found) response **/
        data object FileNotFound : DefaultImage("404")

        /** : (mystery-person) a simple, cartoon-style silhouetted outline of a person (does not vary by email hash) **/
        data object MysteryPerson : DefaultImage("mp")

        /** : a geometric pattern based on an email hash **/
        data object Geometric : DefaultImage("identicon")

        /** : a generated ‘monster’ with different colors, faces, etc **/
        data object Monster : DefaultImage("monsterid")

        /** : generated faces with differing features and backgrounds */
        data object Face : DefaultImage("wavatar")

        /** : awesome generated, 8-bit arcade-style pixelated faces */
        data object Retro : DefaultImage("retro")

        /** : a generated robot with different colors, faces, etc */
        data object Robot : DefaultImage("robohash")

        /** : a transparent PNG image (border added to HTML below for demonstration purposes) */
        data object Transparent : DefaultImage("blank")
    }

    internal val sha256 = SHA256()
    private const val HOST = "https://gravatar.com"

    @ExperimentalStdlibApi
    fun getAvatarUrl(
        email: String,
        sizeInPixels: Int = 1024,
        defaultImage: DefaultImage? = null,
        rating: Rating = Rating.g,
        forceDefault: Boolean = false,
    ): Result<String> =
        runCatching {
            require(sizeInPixels in 1..2048) { "The sizeInPixels parameter must be between 1 and 2048." }

            val emailHash = requireHashed(email)

            return Result.success("$HOST/avatar/$emailHash?s=$sizeInPixels&r=${rating}${defaultImage?.params?.let { "&d=$it" } ?: ""}${
                if (forceDefault) {
                    "&f=true"
                } else {
                    ""
                }
            }")
        }

    @ExperimentalStdlibApi
    internal fun requireHashed(email: String): String =
        sha256.digest(email.lowercase().trim().encodeToByteArray()).toHexString()

    @ExperimentalStdlibApi
    private fun hashed(email: String?): String? = email?.let {
        requireHashed(it).toString()
    }
}
