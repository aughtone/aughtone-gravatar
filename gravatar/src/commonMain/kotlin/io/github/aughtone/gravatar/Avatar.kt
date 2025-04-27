package io.github.aughtone.gravatar

import io.github.aughtone.gravatar.Avatar.Rating.g
import io.github.aughtone.gravatar.Avatar.Rating.pg
import io.github.aughtone.gravatar.Avatar.Rating.r
import io.github.aughtone.gravatar.Avatar.Rating.x
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Avatar(
    @SerialName("image_id")
    val imageId: String,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("rating")
    val rating: String, //: "G",
    @SerialName("alt_text")
    val altText: String, // "Gravatar's avatar image. Gravatar is a service for providing globally unique avatars.",
    @SerialName("selected")
    val selected: Boolean, // true,
    @SerialName("updated_date")
    val updatedDate: String, //": "2021-10-01T12:00:00Z"
) {
    /**
     * Represents the content rating for Gravatar images.
     *
     * @see <a href="https://en.gravatar.com/site/implement/images/#rating">Gravatar Ratings</a>
     *
     * @property g suitable for display on all websites with any audience type.
     * @property pg may contain rude gestures, provocatively dressed individuals, the lesser swear words, or mild violence.
     * @property r may contain such things as harsh profanity, intense violence, nudity, or hard drug use.
     * @property x may contain sexual imagery or extremely disturbing violence.
     */
    enum class Rating {
        /** suitable for display on all websites with any audience type. */
        g,

        /** may contain rude gestures, provocatively dressed individuals, the lesser swear words, or mild violence. */
        pg,

        /** may contain such things as harsh profanity, intense violence, nudity, or hard drug use. */
        r,

        /** may contain sexual imagery or extremely disturbing violence.  */
        x;
    }
}
