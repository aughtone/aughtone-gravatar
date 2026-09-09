# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).


## [Unreleased]

## [1.0.4] - 2026-09-09

### Added
- **Gravatar API v3 Support**: Full support for avatar management (upload, list, activate, delete, update) and profile management (retrieve, update).
- **Coil 3 Integration**: Efficient cross-platform image loading.
- **API Response Validation**: Explicit HTTP status checks across all `GravatarApi` endpoints, parsing and propagating structured server error messages on failures instead of throwing deserialization errors.
- **`GravatarApiException`**: Failed API calls now fail with a typed exception carrying the HTTP `status`, plus `isNotFound`, `isUnauthorized`, `isRateLimited` and `isServerError`. Branch on the status rather than on the server-supplied message text.

### Changed
- **Android HTTP engine is now CIO**: `ktor-client-okhttp` has been replaced by `ktor-client-cio` on Android, removing OkHttp from the transitive dependencies of anyone consuming this library.
- **Avatar URL space encoding**: A space in the `name` parameter of `DefaultImage.Initials` is now encoded as `%20` rather than `+`, following `aughtone-types` 3.x. Both forms are accepted by Gravatar, but generated URLs differ from 1.0.3.
- **Branding & Standardization**: Renamed "AughtOne" to "Aughtone" across the project, unified the iOS kit naming to `AughtoneGravatarKit`, and standardized the `namespace` to `io.github.aughtone.gravatar`.
- **Dependency Updates**: `aughtone-types` 3.4.0, Kotlin 2.4.0, Ktor 3.5.0, Coil 3.5.0.
- **Memory**: `org.gradle.jvmargs` is no longer set in the repository so developers can supply their own local overrides; CI passes memory settings explicitly.

### Fixed
- **Avatar URLs for an already-hashed identifier**: `getAvatarUrl` hashed its input unconditionally, so passing a hash (as `Profile.hash` does) produced a second hash and a URL for an account that does not exist. It now accepts either an email or an existing hash, matching `getProfileUrl` and `getQrCodeUrl`.
- **JavaScript browser builds**: `ktor-client-cio` was declared in `commonMain` and reached the JS target, pulling in `ktor-network` and a `node:net` dependency that cannot resolve in a browser. CIO is now declared only for the JVM and Android targets.

### Removed
- **AI Skill Apparatus**: Removed the embedded `*.ai-skill.md` resources, the `.agents/skills/` capability index, and the `docs/standards/` skill documents. The library no longer ships or consumes machine-readable agent skills.

### Not published
- **`gravatar-compose`**: The Compose Multiplatform module builds and is tested in this release, but its Maven publication is disabled pending a review of its public API. It is not available from Maven Central.

## [1.0.3] - 2026-04-23

### Changed
- **Toolchain Upgrade**: Upgraded to **Android Gradle Plugin 9.1.1** and **Kotlin 2.3.20**.
- **Platform Alignment**: Updated **Compile SDK to 37** and transitioned to the `com.android.kotlin.multiplatform.library` plugin for improved KMP support.
- **Dependency Refresh**: Synchronized with the `io.github.aughtone:types:2.0.0` stable release.

## [1.0.1] - 2026-04-23


### Added
- **AI-Skill Integration**: Published the `io.github.aughtone.gravatar.ai-skill.md` machine-readable skill to expose Gravatar hashing and avatar lookup capabilities to AI agents.
- **Project Governance**: Initialized the 5-sector documentation hierarchy (`ARCH.md`, `SPEC.md`, `DEVELOPER.md`) and core standards for KMP development.
- **Agent Instructions**: Added `AGENTS.md` to guide AI contributors on repository structure and quality engineering protocols.

### Changed
- Stabilized dependencies and build configuration for the `1.0.1` release.
