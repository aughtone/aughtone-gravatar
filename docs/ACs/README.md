# Living Acceptance Criteria Index

This document serves as the master record of Acceptance Criteria (ACs) for `aughtone-gravatar`. All features must have corresponding ACs written here before implementation, in accordance with the project's quality engineering standards.

---

## 🔒 Hashing (Identity Management)

### `AC-HASH-01`: Basic SHA256 Hashing
- **Given** an email address of `user@example.com`
- **When** the library generates the Gravatar identifier
- **Then** the output hash must be the correct SHA256 hex string: `27205e5c51cb03f862138b22bcb5dc20f94a342e744ff6df1b8dc8af3c865109`

### `AC-HASH-02`: Email Whitespace Trimming
- **Given** an email address with surrounding whitespace: `  user@example.com  `
- **When** the library generates the Gravatar identifier
- **Then** the leading and trailing whitespace must be trimmed before hashing
- **And** the output hash must match the SHA256 hex string: `27205e5c51cb03f862138b22bcb5dc20f94a342e744ff6df1b8dc8af3c865109`

### `AC-HASH-03`: Email Lowercasing
- **Given** an email address containing mixed-case characters: `User@Example.com`
- **When** the library generates the Gravatar identifier
- **Then** all characters must be converted to lowercase before hashing
- **And** the output hash must match the SHA256 hex string: `27205e5c51cb03f862138b22bcb5dc20f94a342e744ff6df1b8dc8af3c865109`

---

## 🌍 Gravatar API v3 Retrieval

### `AC-API-01`: Profile Retrieval Success
- **Given** a valid and public email hash `99511d6010af8c574c31f94e1b327bba5e25086dd7b92a4b6f3e132b579cc8d1`
- **When** calling `getProfile` with the hash
- **Then** the API must return a `Success` result containing the correct parsed `Profile` model (hash, display name, profile URL, avatar URL).

### `AC-API-02`: Profile Not Found
- **Given** an invalid or non-existent email hash
- **When** calling `getProfile` with the hash
- **Then** the API must return a `Failure` result containing a `404 Not Found` error response.

### `AC-API-03`: Authenticated Request Header
- **Given** a `GravatarApi` initialized with API Key `TEST_API_KEY`
- **When** any API request is made
- **Then** the HTTP request must include the `Authorization: Bearer TEST_API_KEY` header.

---

## 🎨 Visual Components (Compose Multiplatform)

### `AC-UI-01`: Avatar Loading State
- **Given** a `GravatarImage` component with a valid email
- **When** the Coil 3 image loader is fetching the avatar from the network
- **Then** the component must display the placeholder/shimmer state.

### `AC-UI-02`: Avatar Loading Failure
- **Given** a `GravatarImage` component where the network fetch fails
- **When** the error fallback is triggered
- **Then** the component must render the specified default fallback avatar (e.g., `mp`).
