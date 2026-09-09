package io.github.aughtone.gravatar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SetEmailAvatarRequest(
    @SerialName("email_hash")
    val emailHash: String,
)


@Serializable
data class UpdateAvatarRequest(
    @SerialName("rating")
    val rating: Avatar.Rating? = null,
    @SerialName("alt_text")
    val altText: String? = null,
)

@Serializable
data class UpdateProfileRequest(
    @SerialName("display_name")
    val displayName: String? = null,
    @SerialName("location")
    val location: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("pronouns")
    val pronouns: String? = null,
    @SerialName("job_title")
    val jobTitle: String? = null,
    @SerialName("company")
    val company: String? = null,
)
