---
skill-id: io.github.aughtone.gravatar
scope: core
compatibility: ">=1.0.0"
---

# AI Skill: Aughtone Gravatar

This library provides utilities for generating Gravatar avatar URLs in Kotlin Multiplatform. It handles email normalization, SHA-256 hashing, and parameter encoding automatically.

## 🧰 The AI Toolbox (Key Functions)

### **URL Generation**
- `gravatarUrlOf(email: String, sizeInPixels: Int = 1024, ...): String?`
    - **Usage**: `val url = gravatarUrlOf("user@example.com", sizeInPixels = 512)`
- `gravatarUrlOf(email: String, name: String, ...): String?`
    - **Usage**: `val url = gravatarUrlOf("user@example.com", "John Doe")`
    - **Note**: Automatically generates initials as a fallback image using the provided name.

### **Default Image Options (`DefaultImage`)**
When no Gravatar is found for an email, you can specify a fallback:
- `DefaultImage.Initials(name = "...")`: Generates initials based on the name.
- `DefaultImage.Retro`: 8-bit arcade-style pixelated faces.
- `DefaultImage.Geometric`: Geometric patterns (Identicon).
- `DefaultImage.Monster`: Random monster faces.
- `DefaultImage.Robot`: Random robot faces.
- `DefaultImage.MysteryPerson`: Silhouetted outline.

## 📜 Compliance & Standards

- **Hashing**: Uses **SHA-256** for email hashing (the modern Gravatar standard).
- **Normalization**: Automatically trims and lowercases email addresses before hashing.
- **Rating**: Supports Gravatar ratings (`g`, `pg`, `r`, `x`). Defaults to `g`.

## 🤖 Agent Onboarding
1. **Context Registration**: Add this skill file to the `AGENTS.md` of the consuming project.
2. **README Verification**: Ensure the root `README.md` contains the "AI-Assisted Development" section.
3. **Usage Rules**:
    - **Privacy**: Never log or store raw email addresses; only handle them via `gravatarUrlOf` which hashes them internally.
    - **Consistency**: Use the `Initials` fallback for user profiles where a name is available to provide a better UX than a generic silhouette.
