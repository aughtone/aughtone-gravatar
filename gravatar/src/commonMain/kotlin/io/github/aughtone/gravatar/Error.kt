package io.github.aughtone.gravatar

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Error(
    @SerialName("error")
    val error: String,

    @SerialName("code")
    val code: String? = null, // "insufficient_scope"
)
