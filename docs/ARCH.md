# Architecture Guidelines

## 📦 Distribution & Publishing

Aughtone Gravatar is a Kotlin Multiplatform library published to **Maven Central** using the `com.vanniktech.maven-publish` plugin.

### Coordinates
- **Group**: `io.github.aughtone`
- **Artifacts**: 
    - `gravatar`: Core library (Hashing, API client, Data models).
    - `gravatar-ui`: UI components (Compose Multiplatform).
- **Version**: Managed via `libs.versions.versionName`

### Infrastructure
- **Plugin**: `com.vanniktech.maven-publish`
- **Target**: Maven Central (OSSRH)
- **Planned Support**: NPM/JS distribution for the core `gravatar` logic.
- **Automatic Release**: Enabled (`automaticRelease = true`)
- **Signing**: Mandatory GPG signing (can be bypassed with `-Pskip-signing` for local builds).
