package io.github.aughtone.gravatar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Associated(
    @SerialName("associated")
    val associated: Boolean,
)
