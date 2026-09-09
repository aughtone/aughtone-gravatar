package io.github.aughtone.gravatar.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.aughtone.gravatar.Profile
import io.github.aughtone.gravatar.UpdateProfileRequest

@Composable
fun GravatarEditProfileView(
    profile: Profile,
    onSave: (UpdateProfileRequest) -> Unit,
    modifier: Modifier = Modifier,
) {
    var displayName by remember { mutableStateOf(profile.displayName ?: "") }
    var location by remember { mutableStateOf(profile.location ?: "") }
    var description by remember { mutableStateOf(profile.description ?: "") }
    var jobTitle by remember { mutableStateOf(profile.jobTitle ?: "") }
    var company by remember { mutableStateOf(profile.company ?: "") }
    var pronouns by remember { mutableStateOf(profile.pronouns ?: "") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Edit Gravatar Profile",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = displayName,
            onValueChange = { displayName = it },
            label = { Text("Display Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pronouns,
            onValueChange = { pronouns = it },
            label = { Text("Pronouns") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = jobTitle,
            onValueChange = { jobTitle = it },
            label = { Text("Job Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = company,
            onValueChange = { company = it },
            label = { Text("Company") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Bio") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Button(
            onClick = {
                onSave(
                    UpdateProfileRequest(
                        displayName = displayName.ifBlank { null },
                        location = location.ifBlank { null },
                        description = description.ifBlank { null },
                        pronouns = pronouns.ifBlank { null },
                        jobTitle = jobTitle.ifBlank { null },
                        company = company.ifBlank { null }
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}
