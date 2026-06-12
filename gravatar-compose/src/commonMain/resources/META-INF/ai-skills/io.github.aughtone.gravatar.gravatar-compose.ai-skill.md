---
skill-id: io.github.aughtone.gravatar.gravatar-compose
spec-version: "1.0"
type: "Aughtone AI-Skill"
scope: compose
compatibility: ">=1.0.0"
---

# AI Skill: Aughtone Gravatar Compose

This library provides Compose Multiplatform UI components for displaying and editing Gravatar data.

## 🧰 The AI Toolbox

### **1. Image Components**
- `GravatarImage(email, size, modifier, ...)`: Displays an avatar image for a given email address. Handles SHA256 hashing and async loading via Coil 3.
- `GravatarImage(hash, size, modifier, ...)`: Displays an avatar image using a pre-calculated hash.

### **2. Profile Components**
- `GravatarProfileView(profile, modifier)`: A read-only profile card displaying user information (name, bio, location, etc.).
- `GravatarEditProfileView(profile, onSave, onCancel)`: A form for editing Gravatar profile details.

### **3. Preferences**
- Always use `GravatarImage` instead of raw `AsyncImage` for Gravatar resources to ensure correct URL construction and hashing.
- Prefer passing the email address directly to `GravatarImage` unless you already have the hash.

## 📋 Compliance & Standards
- **UI Consistency**: Follows Material 3 design principles by default.
- **Accessibility**: Includes basic content descriptions for images.

## 🎨 Immutability & Data
- Components are designed to work with the immutable data models from the `gravatar` core library.
- State is managed internally for form fields in `GravatarEditProfileView`, but results are emitted via callbacks.

## 🤖 Agent Onboarding
1. **Context Registration**: Add this skill to the project's `AGENTS.md`.
2. **Library Configuration**: 
    - Requires `io.github.aughtone.gravatar:gravatar` as a dependency.
    - Requires Compose Multiplatform and Coil 3.
3. **Usage Rules**:
    - Ensure a `Coil` image loader is configured in the application context (usually handled by the library's defaults or the consuming app).
