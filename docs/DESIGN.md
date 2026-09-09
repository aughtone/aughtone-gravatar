# Design & UI Guidelines

This document outlines the design language, User Stories, and Compose Multiplatform (CMP) UI guidelines for `gravatar-compose`.

---

## 🎨 Design System & Visual language

Components in `gravatar-compose` must follow modern, cohesive UI/UX standards:
- **Design Framework**: Align to **Material Design 3 (M3)** components, color schemes, and typography.
- **Theme Adaptability**: Support dynamic dark/light mode switches, respecting the local application's `MaterialTheme`.
- **Typography**: Inherit system typography or allow custom text styles for names, handles, and descriptions.

---

## 🖼️ Image Loading & Rendering Guidelines

Gravatar relies on dynamic HTTP image resources. To ensure a premium user experience, the following rules apply to `GravatarImage`:
1. **Asynchronous Loading**: Powered by **Coil 3** to load images efficiently across all KMP targets (Android, iOS, JVM, JS/Wasm).
2. **Placeholders & Fallbacks**:
   - Provide a skeleton/shimmer placeholder or custom fallback icon while the image is loading.
   - If the image fails to load, render a placeholder matching the requested fallback strategy (e.g. Gravatar default types like `mp`, `identicon`, `retro`).
3. **Animations**: Use subtle transitions (e.g., crossfade) when images resolve to prevent sudden UI jumps.

---

## 👥 User Stories (Goal-Oriented Design)

Each UI component addresses specific target scenarios.

### 👤 `STORY-CMP-IMAGE`: Avatar Display
- **Goal**: As a developer, I want to display a user's Gravatar avatar by passing their email or pre-calculated hash.
- **Visual Spec**:
  - Support round/circular clipping (`circle = true`).
  - Allow specifying custom size dimensions (e.g., size in DP).
  - Automatically construct the correct Gravatar avatar URL with the required SHA256 hashed value.

### 🎴 `STORY-CMP-PROFILE-VIEW`: Profile Card
- **Goal**: As a user, I want to see a read-only profile card that neatly displays my display name, bio, location, job, and social links.
- **Visual Spec**:
  - Layout: Hero header image, avatar overlapping the header, profile name/pronouns, body info (bio, location), and a grid/list of verified accounts or custom links.
  - Hover / Interactive elements: Hovering or clicking links/social platforms should trigger platform-native URI launching.

### ✏️ `STORY-CMP-PROFILE-EDIT`: Profile Editor Form
- **Goal**: As a user, I want to edit my Gravatar profile details (display name, bio, location) inside the app and save changes.
- **Visual Spec**:
  - Input fields must adhere to UDF state models.
  - Present standard Material 3 text inputs (with validation states).
  - Clear Save and Cancel action buttons.
  - Emit structured model changes on successful validation.

---

## 🧠 State & ViewModel Governance (UDF)

For interactive screens (such as `GravatarEditProfileView`), implement **Unidirectional Data Flow (UDF)**:
1. **Immutable State**: State is held in a single, immutable data class representing form values, validation flags, and submission states.
2. **State Updates**: All state transformations must return a new copy of the state model (`state.copy(...)`).
3. **Side Effects**: Handle asynchronous actions (like API calls to update profiles) using Kotlin Coroutines via structured events/effects.
