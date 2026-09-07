package io.github.aughtone.gravatar.sample

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import io.github.aughtone.gravatar.GravatarApi
import io.github.aughtone.gravatar.Profile
import io.github.aughtone.gravatar.compose.GravatarProfile
import io.github.aughtone.gravatar.compose.GravatarEditProfile

@Composable
fun App(defaultApiKey: String? = null) {
    var apiKey by remember { mutableStateOf(defaultApiKey ?: "") }
    var emailOrHash by remember { mutableStateOf("example@example.com") }
    var triggerSearch by remember { mutableStateOf(0) }
    var isEditing by remember { mutableStateOf(false) }
    var loadedProfile by remember { mutableStateOf<Profile?>(null) }
    var apiErrorMessage by remember { mutableStateOf<String?>(null) }

    val api = remember(apiKey) {
        GravatarApi(apiKey = apiKey.ifBlank { null })
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Gravatar Compose Sample",
                    style = MaterialTheme.typography.headlineLarge
                )

                OutlinedTextField(
                    value = apiKey,
                    onValueChange = { apiKey = it },
                    label = { Text("API Key (Bearer Token)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = emailOrHash,
                        onValueChange = { emailOrHash = it },
                        label = { Text("Email or MD5/SHA256 Hash") },
                        modifier = Modifier.weight(1f)
                    )

                    Button(
                        onClick = {
                            isEditing = false
                            loadedProfile = null
                            apiErrorMessage = null
                            triggerSearch++
                        }
                    ) {
                        Text("Search")
                    }
                }

                HorizontalDivider()

                if (apiErrorMessage != null) {
                    val clipboardManager = LocalClipboardManager.current
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = apiErrorMessage ?: "",
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                            TextButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(apiErrorMessage ?: ""))
                                },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text("Copy Error")
                            }
                        }
                    }
                }

                if (triggerSearch > 0) {
                    key(triggerSearch) {
                        if (isEditing && loadedProfile != null) {
                            GravatarEditProfile(
                                profile = loadedProfile!!,
                                api = api,
                                onSaveSuccess = { updated ->
                                    loadedProfile = updated
                                    isEditing = false
                                },
                                onSaveError = { err ->
                                    apiErrorMessage = "Save failed: ${err.message}"
                                },
                                modifier = Modifier.fillMaxWidth().weight(1f)
                            )
                        } else {
                            GravatarProfile(
                                emailOrHash = emailOrHash,
                                api = api,
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                successContent = { profile ->
                                    LaunchedEffect(profile) {
                                        loadedProfile = profile
                                    }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        Button(onClick = { isEditing = true }) {
                                            Text("Edit Profile")
                                        }
                                        io.github.aughtone.gravatar.compose.GravatarProfileView(profile = profile)
                                    }
                                }
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Enter an email and search to see the profile.")
                    }
                }
            }
        }
    }
}
