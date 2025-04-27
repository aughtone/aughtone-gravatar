package io.github.aughtone.gravatar

import io.github.aughtone.gravatar.Avatar.Rating
import io.github.aughtone.gravatar.Gravatar.DefaultImage


/**
 * Retrieves the Gravatar URL for a given email address.
 *
 * @param email The email address associated with the Gravatar.
 * @param sizeInPixels The desired size of the avatar in pixels (default: 1024).
 * @param defaultImage The default image to use if no Gravatar is found (default: null).
 *                     Can be a [DefaultImage.Url], [DefaultImage.Identicon],
 *                     [DefaultImage.Monsterid], [DefaultImage.Wavatar],
 *                     [DefaultImage.Retro], [DefaultImage.Blank]
 *                     or [DefaultImage.Initials].
 * @param rating The maximum rating of the Gravatar (default: [Rating.g]).
 * @param forceDefault Whether to force the use of the default image (default: false).
 * @return The Gravatar URL, or null if an error occurred.
 */
@OptIn(ExperimentalStdlibApi::class)
fun gravatarUrlOf(
    email: String,
    sizeInPixels: Int = 1024,
    defaultImage: DefaultImage? = null,
    rating: Rating = Rating.g,
    forceDefault: Boolean = false,
): String? = Gravatar.getAvatarUrl(
    email = email,
    sizeInPixels = sizeInPixels,
    defaultImage = defaultImage,
    rating = rating,
    forceDefault = forceDefault
).getOrNull()


/**
 * Gets the Gravatar URL for the given email, using the provided name to generate default image initials.
 *
 * @param email The email address associated with the Gravatar.
 * @param name The name to use for generating initials if no Gravatar is found.
 * @param sizeInPixels The size of the Gravatar image in pixels (square). Defaults to 1024.
 * @param rating The maximum allowed rating of the Gravatar. Defaults to [Rating.g].
 * @return The URL of the Gravatar image, or `null` if there was an error retrieving it.
 */
@OptIn(ExperimentalStdlibApi::class)
fun gravatarUrlOf(
    email: String,
    name: String,
    sizeInPixels: Int = 1024,
    rating: Rating = Rating.g,
): String? = Gravatar.getAvatarUrl(
    email = email,
    sizeInPixels = sizeInPixels,
    defaultImage = DefaultImage.Initials(name = name),
    rating = rating,
    forceDefault = false
).getOrNull()
