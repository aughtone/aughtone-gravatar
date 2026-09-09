import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

group = libs.versions.namespace.get()
version = "1.0.0"

kotlin {
    androidTarget()
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":gravatar"))
                implementation(project(":gravatar-compose"))

                implementation(libs.kotlinx.datetime)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)

                implementation(libs.coil.compose)
                implementation(libs.coil.network.ktor)

                implementation(libs.kotlinx.coroutines.core)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.android)
                implementation(libs.androidx.activity.compose)
            }
        }

        jvmMain {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

android {
    namespace = "io.github.aughtone.gravatar.sample"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "io.github.aughtone.gravatar.sample"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.compileSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

compose {
    desktop {
        application {
            mainClass = "io.github.aughtone.gravatar.sample.MainKt"
            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Pkg, TargetFormat.Deb)
                packageVersion = "1.0.0"
            }
        }
    }
}
