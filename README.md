[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
![Maven Central Version](https://img.shields.io/maven-central/v/io.github.aughtone/gravatar?style=flat)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.10-blue.svg?logo=kotlin&style=flat)](http://kotlinlang.org)
[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-brightgreen?logo=kotlin)](https://github.com/JetBrains/compose-multiplatform)

![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-ios](http://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat)
![badge-desktop](http://img.shields.io/badge/platform-desktop-DB413D.svg?style=flat)
![badge-js](http://img.shields.io/badge/platform-js%2Fwasm-FDD835.svg?style=flat)

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
- **Compose Multiplatform (CMP) Components**: Ready-to-use UI components for displaying and editing Gravatar data.
- **Coil 3 Integration**: Efficient image loading across all CMP platforms.

# Installation

### Kotlin Multiplatform / Android (Gradle)
Add the dependency to your version catalog or build file:

```kotlin
[libraries]
gravatar = { module = "io.github.aughtone:gravatar", version.ref = "gravatar" }
gravatar-compose = { module = "io.github.aughtone:gravatar-compose", version.ref = "gravatar" }
```

### iOS / Swift (Swift Package Manager or CocoaPods)
The library is distributed as a XCFramework. You can integrate `AughtoneGravatarKit` or `AOGravatarCompose` directly into your Xcode project.

### JavaScript / Node.js (NPM)
*Note: NPM publishing is currently being configured.*
Once available, you can install via:
```bash
npm install @aughtone/gravatar
```

# Usage by Platform

### 📱 Android & Compose Multiplatform
Use the `gravatar-compose` module for seamless integration with Compose:
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

### 🌐 JavaScript / TypeScript
For JS environments, the library provides TypeScript definitions:
```typescript
import { GravatarApi } from '@aughtone/gravatar';

const api = new GravatarApi();
const profile = await api.getProfile('user@example.com');
```

# Quick Start

### Basic Avatar URL
```kotlin
val url = gravatarUrlOf(email = "user@example.com", name = "John Doe")
```

### Using the API (v3)
```kotlin
val api = GravatarApi(apiKey = "your_api_key")
val profile = api.getProfile(emailOrHash = "user@example.com").getOrThrow()
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

---
## 🤖 AI-Assisted Development
This library includes embedded, machine-readable "skills" to help AI assistants understand its APIs and best practices.

- **Discovery**: Look for `META-INF/ai-skills/*.ai-skill.md`

### 🪄 Magic Prompt for AI Assistants
If you are using an AI assistant (like Claude, Gemini, or ChatGPT) to write code with this library, paste this prompt first:

> "Scan all project dependencies for AI Skill files in `META-INF/ai-skills/` with the prefix `io.github.aughtone.gravatar`. Use these to understand the API patterns, types, and governance for this library. If they are not found in the local classpath, refer to https://github.com/aughtone/aughtone-gravatar for the source definitions."
