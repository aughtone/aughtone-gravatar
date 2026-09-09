[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
![Maven Central Version](https://img.shields.io/maven-central/v/io.github.aughtone/gravatar?style=flat)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.0-blue.svg?logo=kotlin&style=flat)](http://kotlinlang.org)
[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-brightgreen?logo=kotlin)](https://github.com/JetBrains/compose-multiplatform)

![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-ios](http://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat)
![badge-desktop](http://img.shields.io/badge/platform-desktop-DB413D.svg?style=flat)
![badge-js](http://img.shields.io/badge/platform-js-FDD835.svg?style=flat)

# Gravatar Multiplatform

This library for set up
for [Kotlin Multiplatform](https://www.jetbrains.com/kotlin-multiplatform/) (KMP)

This is a Kotlin Multiplatform compatible library for accessing [Gravatar](https://gravatar.com/)
resources.

Generating an [avatar image URL](https://docs.gravatar.com/api/avatars/images/) does not require any
additional, but if you need to use the [Gravatar REST API](https://gravatar.com/developers/console)
you will need to make sure [Ktor](https://ktor.io/) runs properly in your project.

Feel free to fork it and make improvements, I'll keep up as best I can.

# Features

- **Avatar URL Generation**: Support for SHA256 hashing, custom sizes, ratings, and default image fallbacks.
- **Gravatar API v3 Support**: Full integration for uploading, retrieving, and managing avatars and user profiles.
- **Compose Multiplatform (CMP) Components**: Ready-to-use UI components for displaying and editing Gravatar data. *Not published yet — see below.*
- **Coil 3 Integration**: Efficient image loading across all CMP platforms.

# Installation

### Kotlin Multiplatform / Android (Gradle)
Add the dependency to your version catalog or build file:

```kotlin
[libraries]
gravatar = { module = "io.github.aughtone:gravatar", version.ref = "gravatar" }
```

> **`gravatar-compose` is not published yet.** The Compose Multiplatform module lives in this repository and is built and tested with every release, but its public API has not been reviewed, so it is not available from Maven Central. The UI examples below describe it for when it ships.

### iOS / Swift (Swift Package Manager or CocoaPods)
The library is distributed as an XCFramework. You can integrate `AughtoneGravatarKit` directly into your Xcode project.

### JavaScript
The `js` target is published to Maven Central alongside the other platforms and is consumed through Gradle. There is no NPM package.

# Usage by Platform

### 📱 Android & Compose Multiplatform
Use the `gravatar-compose` module for seamless integration with Compose (not published yet — see Installation):
```kotlin
GravatarImage(
    email = "user@example.com",
    size = 64.dp,
    circle = true
)
```

### 🍎 iOS (Swift)
The library is exported as a framework. You can use it in Swift as follows:
```swift
import AughtoneGravatarKit

let url = Gravatar.shared.getAvatarUrl(email: "user@example.com")
```

### 🌐 JavaScript
Depend on the `js` target from a Kotlin/JS project and use the same API as every other platform:
```kotlin
val api = GravatarApi()
val profile = api.getProfile(emailOrHash = "user@example.com").getOrThrow()
```

# Quick Start

### Basic Avatar URL
```kotlin
val url = gravatarUrlOf(email = "user@example.com", name = "John Doe")
```

### Using the API (v3)
Every call returns a `Result`. A failed call carries a `GravatarApiException` with the HTTP status, so you can branch on the status rather than on the server's message text:
```kotlin
val api = GravatarApi(apiKey = "your_api_key")

api.getProfile(emailOrHash = "user@example.com").fold(
    onSuccess = { profile -> println(profile.displayName) },
    onFailure = { error ->
        when {
            error is GravatarApiException && error.isNotFound -> println("No profile for that address")
            error is GravatarApiException && error.isUnauthorized -> println("Check your API key")
            else -> throw error
        }
    }
)
```

### UI Components (CMP)
```kotlin
// Display an avatar
GravatarImage(email = "user@example.com", size = 64.dp)

// Display a profile card
GravatarProfileView(profile = userProfile)

// Edit profile form
GravatarEditProfileView(profile = userProfile, onSave = { request -> 
    api.updateProfile(request = request)
})
```

# Feedback

Bugs can go into the issue tracker, but you are probably going to get faster support by creating a
PR.

# License

Licensed under the Apache License, Version 2.0 — see [LICENSE](LICENSE). Copyright and attribution are recorded in [NOTICE.md](NOTICE.md).
