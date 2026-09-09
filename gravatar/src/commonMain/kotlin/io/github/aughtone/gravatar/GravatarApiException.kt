package io.github.aughtone.gravatar

/**
 * Thrown when the Gravatar API returns a non-success response.
 *
 * [status] is the HTTP status code. [message] is the error text supplied by the
 * server when the response carried a structured error body, and a generated
 * fallback otherwise — it comes from the remote service, so match on [status]
 * or the predicates below rather than on the text.
 */
class GravatarApiException(
    val status: Int,
    override val message: String,
) : RuntimeException(message) {

    /** No profile or avatar exists for the supplied identifier. */
    val isNotFound: Boolean get() = status == 404

    /** The request was unauthenticated, or the token lacks the required scope. */
    val isUnauthorized: Boolean get() = status == 401 || status == 403

    /** The client has exceeded the API rate limit. */
    val isRateLimited: Boolean get() = status == 429

    /** The failure originated on the server and may succeed if retried. */
    val isServerError: Boolean get() = status in 500..599

    override fun toString(): String = "GravatarApiException(status=$status, message=$message)"
}
