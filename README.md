[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
![Maven Central Version](https://img.shields.io/maven-central/v/io.github.aughtone/gravatar?style=flat)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.10-blue.svg?logo=kotlin&style=flat)](http://kotlinlang.org)
[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-brightgreen?logo=kotlin)](https://github.com/JetBrains/compose-multiplatform)

![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-ios](http://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat)
![badge-desktop](http://img.shields.io/badge/platform-desktop-DB413D.svg?style=flat)
![badge-js](http://img.shields.io/badge/platform-js%2Fwasm-FDD835.svg?style=flat)

# Gravatar Multiplatform

This library for set up
for [Kotlin Multiplatform](https://www.jetbrains.com/kotlin-multiplatform/) (KMP)

This is a Kotlin Multiplatform compatible library for accessing [Gravatar](https://gravatar.com/)
resources.

Generating an [avatar image URL](https://docs.gravatar.com/api/avatars/images/) does not require any
additional, but if you need to use the [Gravatar REST API](https://gravatar.com/developers/console)
you will need to make sure [Ktor](https://ktor.io/) runs properly in your project.

Feel free to fork it and make improvements, I'll keep up as best I can.

# Features

...describe features...

# Installation

![Maven Central Version](https://img.shields.io/maven-central/v/io.github.aughtone/gravatar?style=flat)

Add the dependency to tyour library file:

```gradle
[versions]
aughtone-gravatar = "${version}"

[libraries]
aughtone-gravatar = { module = "io.github.aughtone:gravatar", version.ref = "aughtone-gravatar" }
```

Include the dependency in the module you want to use it with:

```gradle
implementation(libs.aughtone.gravatar)
```

Or if you are still old-school:

```gradle
implementation("io.github.aughtone:gravatar:${version}")
```

# Quick Start

You can get a simple Gravatar url like this:

```kotlin
val avatarUrl: String = gravatarUrl(email = "johndoe@example.com", name = "John Doe")
```

The name property will be used to generate an initials image if no Gravatar is found, so you get an
output like the one with the initials, since that address is not registered with Gravatar:

![image](https://gravatar.com/avatar/55e79200c1635b37ad31a378c39feb12f120f116625093a19bc32fff15041149?s=128&r=g&d=initials&initials=JD) ![image](https://gravatar.com/avatar/21ba0fe27eb6ba49492e49beca5431f5f2f053640b41af189bf184edb8b26b62?s=128&r=g&d=initials&initials=BP)

# Feedback

Bugs can go into the issue tracker, but you are probably going to get faster support by creating a
PR.   
