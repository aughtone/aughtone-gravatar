---
skill-id: io.github.aughtone.gravatar
spec-version: 1.1
name: "[Aughtone Gravatar](https://github.com/aughtone/aughtone-gravatar)"
type: "Aughtone AI-Skill"
scope: core
compatibility: ">=1.1.0"
author: "[Brill Pappin](https://github.com/bpappin)"
---

# AI Skill: Aughtone Gravatar

This library provides a type-safe Kotlin Multiplatform wrapper for Gravatar services, supporting URL generation, API v3 management, and Compose Multiplatform UI components.

## 🧰 The AI Toolbox (Key Components)

### **1. URL Generation**
The primary entry point for simple avatar links.
- `gravatarUrlOf(email, name, sizeInPixels, rating)`
- `Gravatar.getAvatarUrl(email, sizeInPixels, defaultImage, rating, forceDefault)`
    - **Note**: Uses SHA256 hashing for modern Gravatar compliance.

### **2. Gravatar API v3**
Use `GravatarApi` for authenticated management.
- `getProfile(emailOrHash)`: Retrieve full user `Profile`.
- `uploadAvatar(imageData, ...)`: Upload new image to gallery.
- `activateAvatar(imageId, emails)`: Set active avatar for specific addresses.
- `updateProfile(request)`: Update user bio, location, display name, etc.

### **3. UI Components (Compose Multiplatform)**
Available in `io.github.aughtone.gravatar.ui`.
- `GravatarImage(email, size, ...)`: Async image loading with Coil 3.
- `GravatarProfileView(profile)`: Standardized profile card display.
- `GravatarEditProfileView(profile, onSave)`: Profile editing form.

## 🤖 Agent Onboarding
1. **Context Registration**: Refer to `AGENTS.md` in this repository for structure.
2. **Library Selection**:
    - Use `gravatar` for logic/API (JVM, Android, iOS, JS, Linux).
    - Use `gravatar-ui` for Compose Multiplatform UI.
3. **Usage Rules**:
    - **Email Hashing**: The library automatically handles SHA256 (trim/lowercase). Use `Gravatar.requireHashed(email)` if you need the raw hash.
    - **Authentication**: Write operations require a Bearer token (`apiKey`).
    - **Performance**: `GravatarImage` handles caching via Coil; avoid manual image management.
    - **Heap Size**: iOS framework linking requires `Xmx8G`. Ensure `gradle.properties` is configured.
