# Functional Specifications

## 🌍 Domain Context

Aughtone Gravatar provides a standardized, type-safe foundation for integrating Gravatar services into Kotlin Multiplatform applications.

### Public Identity
The library is distributed globally via Maven Central under the following coordinates:
- **Group ID**: `io.github.aughtone`
- **Artifact ID**: `gravatar` / `gravatar-compose`

### Core Capabilities
1. **Identity Management**: Secure SHA256 hashing for user identification.
2. **Resource Retrieval**: Seamless fetching of avatar URLs and full user profiles via API v3.
3. **Asset Management**: Full CRUD operations for user avatars (upload, update, delete).
4. **Visual Integration**: Pre-built Compose Multiplatform components for profile display and management.

---

## 📜 Authoritative Source Reference

This library implements the official **Gravatar API v3.0.0** specification.

- **Official Source**: [Use with AI assistants — Gravatar For Developers](https://docs.gravatar.com/guides/llms-txt/)
- **API Version**: `v3` (Semantic versioning `3.0.0`)
- **Base URL**: `https://api.gravatar.com/v3`
- **OpenAPI Specification**: `https://api.gravatar.com/v3/openapi`

### 🔑 Critical Rules from Source
1. **Email Hashing**: Must use **SHA256** hash. MD5 is deprecated.
   - **Steps**: Trim leading/trailing whitespace, convert to lowercase, and generate the SHA256 hex string.
2. **Profiles Endpoint**: `GET /profiles/{profileIdentifier}` where `profileIdentifier` is the SHA256 hash or profile URL slug.
3. **QR Code Endpoint**: `GET /qr-code/{sha256_hash}` (supports optional parameters: `size`, `version`, `type`).
4. **Alternative Formats**: Profiles can also be requested by appending `.json`, `.xml`, `.php`, `.vcf`, or `.md` to the profile URL.

