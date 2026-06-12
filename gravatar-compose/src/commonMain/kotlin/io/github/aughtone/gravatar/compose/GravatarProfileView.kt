package io.github.aughtone.gravatar.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.aughtone.gravatar.Profile

@Composable
fun GravatarProfileView(
    profile: Profile,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        profile.avatarUrl?.let {
            GravatarImage(
                email = profile.hash, // Profile identifier is the hash
                size = 120.dp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        profile.displayName?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        profile.jobTitle?.let { title ->
            val company = profile.company?.let { " at $it" } ?: ""
            Text(
                text = "$title$company",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        profile.description?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }

        profile.location?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}
