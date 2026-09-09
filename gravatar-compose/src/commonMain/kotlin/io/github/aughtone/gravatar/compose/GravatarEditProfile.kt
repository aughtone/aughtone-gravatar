package io.github.aughtone.gravatar.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.aughtone.gravatar.GravatarApi
import io.github.aughtone.gravatar.Profile
import io.github.aughtone.gravatar.UpdateProfileRequest
import kotlinx.coroutines.launch

@Composable
fun GravatarEditProfile(
    profile: Profile,
    api: GravatarApi,
    onSaveSuccess: (Profile) -> Unit,
    onSaveError: (Throwable) -> Unit,
    modifier: Modifier = Modifier,
    oauthToken: String? = null,
) {
    var isSaving by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
        GravatarEditProfileView(
            profile = profile,
            onSave = { request ->
                scope.launch {
                    isSaving = true
                    val result = api.updateProfile(oauthToken = oauthToken, request = request)
                    isSaving = false
                    result.fold(
                        onSuccess = onSaveSuccess,
                        onFailure = onSaveError
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        if (isSaving) {
            // Semi-transparent overlay blocking interaction
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
