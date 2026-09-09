package io.github.aughtone.gravatar.compose

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
import kotlinx.coroutines.launch

sealed interface GravatarProfileState {
    data object Loading : GravatarProfileState
    data class Success(val profile: Profile) : GravatarProfileState
    data class Error(val throwable: Throwable) : GravatarProfileState
}

@Composable
fun GravatarProfile(
    emailOrHash: String,
    api: GravatarApi,
    modifier: Modifier = Modifier,
    oauthToken: String? = null,
    loadingContent: @Composable BoxScope.() -> Unit = {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    },
    errorContent: @Composable BoxScope.(Throwable, onRetry: () -> Unit) -> Unit = { error, onRetry ->
        val clipboardManager = LocalClipboardManager.current
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        ) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Failed to load profile",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = error.message ?: "Unknown error",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onRetry,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Retry")
                    }
                    OutlinedButton(
                        onClick = {
                            val errorDetails = buildString {
                                appendLine("Error: ${error.message}")
                                appendLine(error.stackTraceToString())
                            }
                            clipboardManager.setText(AnnotatedString(errorDetails))
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onErrorContainer)
                    ) {
                        Text("Copy Error")
                    }
                }
            }
        }
    },
    successContent: @Composable (Profile) -> Unit = { profile ->
        GravatarProfileView(profile = profile)
    }
) {
    var state by remember(emailOrHash) { mutableStateOf<GravatarProfileState>(GravatarProfileState.Loading) }
    val scope = rememberCoroutineScope()

    fun loadProfile() {
        scope.launch {
            state = GravatarProfileState.Loading
            val result = api.getProfile(oauthToken = oauthToken, emailOrHash = emailOrHash)
            state = result.fold(
                onSuccess = { GravatarProfileState.Success(it) },
                onFailure = { GravatarProfileState.Error(it) }
            )
        }
    }

    LaunchedEffect(emailOrHash, api, oauthToken) {
        loadProfile()
    }

    Box(modifier = modifier) {
        when (val currentState = state) {
            is GravatarProfileState.Loading -> loadingContent()
            is GravatarProfileState.Error -> errorContent(currentState.throwable) { loadProfile() }
            is GravatarProfileState.Success -> successContent(currentState.profile)
        }
    }
}
