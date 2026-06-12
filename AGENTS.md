# Project Development Guide (AGENTS.md)

This document is the master instruction set for AI agents contributing to this repository.

## 1. Documentation Governance
This repository follows the **5-sector hierarchy**. All knowledge must be dispersed into:
- 📐 Architecture (docs/ARCH.md)
- 🧠 Functional Specifications (docs/SPEC.md)
- 🎨 Design & UI (docs/DESIGN.md)
- 📋 Acceptance Criteria (docs/ACs/README.md)
- 📖 Developer Guide (docs/DEVELOPER.md)
- 📜 Changelog (docs/CHANGELOG.md)

## 2. Core Development Principles
- **Test-Driven Development (TDD)**: Whenever feasible, write a failing test before implementation.
- **Kotlin Multiplatform**: All code must be multiplatform-first. Be mindful of source set placement (`commonMain`, `androidMain`, etc.).
- **Immutability & Safety**: Maintain data structure immutability and handle serialization (`kotlinx.serialization`) correctly.
- **Consistency**: Adhere to existing patterns; consistency outweighs personal preference.

## 3. Interaction Rules
- **Plan-First**: Always present a detailed implementation plan before execution.
- **Mandatory Approval**: WAIT for explicit user approval before executing any code changes or tool calls that modify the repository state.
- **Verification**: Check corresponding Acceptance Criteria (ACs) before implementation.

## 4. AI Skill Integration
This library uses machine-readable skills to govern AI behavior and API usage.
- **Core Skill**: `gravatar/src/commonMain/resources/META-INF/ai-skills/io.github.aughtone.gravatar.gravatar.ai-skill.md`
- **UI Skill**: `gravatar-compose/src/commonMain/resources/META-INF/ai-skills/io.github.aughtone.gravatar.gravatar-compose.ai-skill.md`
- **Standard**: Adheres to the [AI Skill Publishing Standard](docs/standards/ai-skill-publishing.md).

Always refer to the patterns defined in the skill files when generating or modifying code.
