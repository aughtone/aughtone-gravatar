---
skill-id: io.github.aughtone.gravatar
name: "[Aughtone Gravatar](https://github.com/aughtone/aughtone-gravatar)"
type: "Aughtone AI-Skill"
scope: core
compatibility: ">=1.0.0"
author: "[Brill Pappin](https://github.com/bpappin)"
---

# AI Skill: Aughtone Gravatar

This library provides a type-safe Kotlin Multiplatform wrapper for the Gravatar API. It allows for easy generation of Gravatar URLs with support for all standard parameters like size, default images, and ratings.

## 🧰 The AI Toolbox (Key Components)

### **Gravatar URL Generation**
The primary entry point is the `gravatarUrl` extension function.
- `String.gravatarUrl(size: Int?, default: DefaultImage?, rating: Rating?, forceDefault: Boolean?): String`
    - **Usage**: Call this on any email string to get its Gravatar image URL.

### **Parameter Enums**
- `DefaultImage`: `404`, `Mp`, `Identicon`, `MonsterId`, `Wavatar`, `Retro`, `Robohash`, `Blank`.
- `Rating`: `G`, `Pg`, `R`, `X`.

## 🤖 Agent Onboarding
1. **Context Registration**: Add this skill file to the `AGENTS.md` of the consuming project.
2. **README Verification**: Ensure the root `README.md` contains the "AI-Assisted Development" section.
3. **Usage Rules**:
    - **Email Sanitization**: The library automatically trims and lowercases emails before hashing, but it's good practice to ensure the input is a valid email string.
    - **Size Constraints**: Gravatar supports sizes from 1 to 2048 pixels.
    - **Security**: Gravatar uses MD5 hashing for email addresses. Do not use this for sensitive data beyond image retrieval.
