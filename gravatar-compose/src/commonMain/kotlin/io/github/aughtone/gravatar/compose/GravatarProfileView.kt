package io.github.aughtone.gravatar.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.aughtone.gravatar.Profile

/**
 * Gravatar returns empty strings for unset fields rather than omitting them, so a
 * null check alone is not enough to tell whether a value is actually present.
 */
private fun String?.takeIfPresent(): String? = this?.takeIf { it.isNotBlank() }

@Composable
fun GravatarProfileView(
    profile: Profile,
    modifier: Modifier = Modifier,
) {
    val displayName = profile.displayName.takeIfPresent()
    val jobTitle = profile.jobTitle.takeIfPresent()
    val company = profile.company.takeIfPresent()
    val description = profile.description.takeIfPresent()
    val location = profile.location.takeIfPresent()

    // Either part may be missing; only join them when both are actually present.
    val role = when {
        jobTitle != null && company != null -> "$jobTitle at $company"
        else -> jobTitle ?: company
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        // Spacing is applied between rendered children only, so an absent field
        // collapses entirely instead of leaving a gap behind.
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (profile.avatarUrl.takeIfPresent() != null) {
            GravatarImage(
                email = profile.hash, // getAvatarUrl accepts an email or an existing hash
                size = 120.dp,
                modifier = Modifier.padding(bottom = 8.dp),
            )
        }

        displayName?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }

        role?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
            )
        }

        description?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp),
            )
        }

        location?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.tertiary,
                textAlign = TextAlign.Center,
            )
        }
    }
}
