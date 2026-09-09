package io.github.aughtone.gravatar.sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File

fun main() = application {
    val secretsFile = File(System.getProperty("user.home") + "/.secrets/agents/aughtone-gravatar/gravatar_api_key.txt")
    val defaultKey = if (secretsFile.exists()) secretsFile.readText().trim() else null

    Window(
        onCloseRequest = ::exitApplication,
        title = "Gravatar KMP Sample"
    ) {
        App(defaultApiKey = defaultKey)
    }
}
