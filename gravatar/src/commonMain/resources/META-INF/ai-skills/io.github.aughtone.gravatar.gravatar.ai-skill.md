---
skill-id: io.github.aughtone.gravatar.gravatar
spec-version: "1.0"
type: "Aughtone AI-Skill"
scope: core
compatibility: ">=1.0.0"
---

# AI Skill: Aughtone Gravatar

This library provides a type-safe Kotlin Multiplatform wrapper for Gravatar services, supporting URL generation, API v3 management, and Compose Multiplatform UI components.

## 🧰 The AI Toolbox

### **1. URL Generation**
Primary entry point for generating avatar image URLs.
- `gravatarUrlOf(email, name, sizeInPixels, rating)`: High-level DSL function.
- `Gravatar.getAvatarUrl(...)`: Core implementation.
- **Preference**: Use `gravatarUrlOf` for simple use cases; use `Gravatar` companion object for granular control.

### **2. Gravatar API v3**
Authenticated management of Gravatar profiles and images.
- `GravatarApi(apiKey)`: Main API client.
- `getProfile(emailOrHash)`: Fetch full user `Profile`.
- `updateProfile(UpdateProfileRequest)`: Modify user metadata.
- `uploadAvatar(imageData, ...)`: Add new images to the user's gallery.

### **3. UI Components (Compose Multiplatform)**
- Available via the `gravatar-compose` artifact.
- `GravatarImage`: Coil-powered async image loader.
- `GravatarProfileView`: Standardized profile display.

## 📋 Compliance & Standards
- **Hashing**: Complies with Gravatar's mandatory **SHA256** hashing requirement for email addresses.
- **API Spec**: Implements the [Gravatar REST API v3](https://docs.gravatar.com/api/rest/).
- **Serialization**: Uses `kotlinx.serialization` for all JSON payloads.

## 🎨 Immutability & Data
- **Models**: All API request and response models are defined as immutable `data class` types.
- **State**: The `GravatarApi` is stateless; each request is independent.
- **Thread Safety**: The library is designed for use with Kotlin Coroutines and is thread-safe across all KMP platforms.

## 🤖 Agent Onboarding
1. **Context Registration**: Add this skill to the project's `AGENTS.md`.
2. **Library Configuration**: 
    - Ensure `ktor` and `kotlinx.serialization` are available in the project.
    - For iOS, ensure the Gradle heap size is sufficient (`-Xmx8G`).
3. **Usage Rules**:
    - Always trim and lowercase email addresses before processing (the library does this internally, but it's good practice for callers).
    - Prefer `gravatarUrlOf` for quick URL generation in Compose.
    - Always handle `Result` types returned by the `GravatarApi`.
