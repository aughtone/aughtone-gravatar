import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.multiplatformLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = libs.versions.namespace.get()
version = libs.versions.versionName.get()

kotlin {
    jvmToolchain(17)
    jvm()

    android {
        namespace = libs.versions.namespace.get()
        compileSdk {
            version = release(libs.versions.compileSdk.get().toInt())
        }
    }

    // See: https://kotlinlang.org/docs/js-project-setup.html
    js(IR) {
        browser {
            generateTypeScriptDefinitions()
        }
        useEsModules() // Enables ES2015 modules
    }
    listOf(iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "AughtoneGravatarKit"
            isStatic = true
            binaryOption(
                "bundleId",
                libs.versions.namespace.get()
            ) //"app.occurrence"
            binaryOption(
                "bundleShortVersionString",
                libs.versions.versionName.get()
            )
        }
    }

    linuxX64()

    //noinspection WrongGradleMethod
    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.ktor.client.cio)
                implementation(libs.kotlinx.coroutines.android)
            }
        }

        jvmMain {
            dependencies {
                implementation(libs.ktor.client.cio)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
        jsMain {
            dependencies {
                implementation(libs.ktor.client.js)
            }
        }
        linuxMain {
            dependencies {
                implementation(libs.ktor.client.curl)
            }
        }
        commonMain {
            dependencies {
                //put your multiplatform dependencies here
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlincrypto.digest)
                implementation(libs.kotlincrypto.sha2)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.resources)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.aughtone.types)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.ktor.client.mock)
                implementation(libs.kotlinx.coroutines.test)
            }
        }
    }

    compilerOptions {
        // XXX Activate when this is resolved:
        //  https://youtrack.jetbrains.com/issue/KT-57847/Move-common-for-all-the-backends-module-name-compiler-option-to-the-KotlinCommonCompilerOptions
        // moduleName = "io.github.aughtone.gavatar"
    }
    // XXX Remove when the above is resolved. This is a workaround.
    //  https://youtrack.jetbrains.com/issue/KT-66568/w-KLIB-resolver-The-same-uniquename...-found-in-more-than-one-library

    metadata {
        compilations.all {
            val compilationName = rootProject.name
            compileTaskProvider.configure {
                if (this is KotlinCompileCommon) {
                    moduleName = "${project.group}:${project.name}_$compilationName"
                }
            }
        }
    }
}

//android {
//    namespace = "io.github.aughtone.gravatar"
//    compileSdk = libs.versions.compileSdk.get().toInt()
//    defaultConfig {
//        minSdk = libs.versions.minSdk.get().toInt()
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_17
//    }
//}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)

    if (!project.hasProperty("skip-signing")) {
        signAllPublications()
    }

    coordinates(group.toString(), "gravatar", version.toString())

    pom {
        name = "Aughtone Gravatar"
        description = "A library of reusable types."
        inceptionYear = "2025"
        url = "https://github.com/aughtone/aughtone-gravatar"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "bpappin"
                name = "Brill pappin"
                url = "https://github.com/bpappin"
            }
        }
        scm {
            url = "https://github.com/aughtone/aughtone-gravatar"
            connection = "https://github.com/aughtone/aughtone-gravatar.git"
            developerConnection = "git@github.com:aughtone/aughtone-gravatar.git"
        }
    }
}
