package io.github.aughtone.gravatar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    @SerialName("hash")
    val hash: String,
    @SerialName("display_name")
    val displayName: String? = null,
    @SerialName("profile_url")
    val profileUrl: String? = null,
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    @SerialName("avatar_alt_text")
    val avatarAltText: String? = null,
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
    @SerialName("verified_accounts")
    val verifiedAccounts: List<VerifiedAccount>? = null,
    @SerialName("pronunciation")
    val pronunciation: String? = null,
    @SerialName("timezone")
    val timezone: String? = null,
    @SerialName("languages")
    val languages: List<String>? = null,
    @SerialName("first_name")
    val firstName: String? = null,
    @SerialName("last_name")
    val lastName: String? = null,
    @SerialName("is_organization")
    val isOrganization: Boolean? = null,
    @SerialName("header_image")
    val headerImage: String? = null,
    @SerialName("hide_default_header_image")
    val hideDefaultHeaderImage: Boolean? = null,
    @SerialName("background_color")
    val backgroundColor: String? = null,
    @SerialName("links")
    val links: List<Link>? = null,
    @SerialName("interests")
    val interests: List<Interest>? = null,
    @SerialName("payments")
    val payments: Payments? = null,
    @SerialName("contact_info")
    val contactInfo: ContactInfo? = null,
    @SerialName("gallery")
    val gallery: List<Avatar>? = null,
    @SerialName("number_verified_accounts")
    val numberVerifiedAccounts: Int? = null,
    @SerialName("last_profile_edit")
    val lastProfileEdit: String? = null,
    @SerialName("registration_date")
    val registrationDate: String? = null,
)

@Serializable
data class VerifiedAccount(
    @SerialName("service")
    val service: String? = null,
    @SerialName("url")
    val url: String,
    @SerialName("username")
    val username: String? = null,
    @SerialName("verified")
    val verified: Boolean? = null,
    @SerialName("service_type")
    val serviceType: String? = null,
    @SerialName("service_label")
    val serviceLabel: String? = null,
    @SerialName("service_icon")
    val serviceIcon: String? = null,
    @SerialName("is_hidden")
    val isHidden: Boolean? = null,
)

@Serializable
data class Link(
    @SerialName("label")
    val label: String,
    @SerialName("url")
    val url: String,
)

@Serializable
data class Interest(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("slug")
    val slug: String,
)

@Serializable
data class CryptoWalletAddress(
    @SerialName("label")
    val label: String,
    @SerialName("address")
    val address: String,
)

@Serializable
data class ContactInfo(
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("cell_phone")
    val cellPhone: String? = null,
    @SerialName("work_phone")
    val workPhone: String? = null,
    @SerialName("home_phone")
    val homePhone: String? = null,
    @SerialName("contact_form")
    val contactForm: String? = null,
    @SerialName("calendar")
    val calendar: String? = null,
)

@Serializable
data class Payments(
    @SerialName("links")
    val links: List<PaymentLink>? = null,
    @SerialName("crypto_wallets")
    val cryptoWallets: List<CryptoWalletAddress>? = null,
)

@Serializable
data class PaymentLink(
    @SerialName("label")
    val label: String,
    @SerialName("url")
    val url: String,
)


