import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.multiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    // Publishing disabled: the Compose components are not reviewed for a public
    // API yet. Re-enable this and the mavenPublishing block below to release.
//    alias(libs.plugins.vanniktech.mavenPublish)
}

group = libs.versions.namespace.get()
version = libs.versions.versionName.get()

kotlin {
    jvmToolchain(17)
    jvm()

    android {
        namespace = "${libs.versions.namespace.get()}.compose"
        compileSdk {
            version = release(libs.versions.compileSdk.get().toInt())
        }
    }

    listOf(iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "AOGravatarCompose"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":gravatar"))
                
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
            }
        }
    }
}

// Publishing disabled - see the plugins block above.
//mavenPublishing {
//    publishToMavenCentral(automaticRelease = true)
//
//    if (!project.hasProperty("skip-signing")) {
//        signAllPublications()
//    }
//
//    coordinates(group.toString(), "gravatar-compose", version.toString())
//
//    pom {
//        name = "AOGravatar UI"
//        description = "Compose Multiplatform components for Gravatar."
//        inceptionYear = "2025"
//        url = "https://github.com/aughtone/aughtone-gravatar"
//        licenses {
//            license {
//                name = "The Apache License, Version 2.0"
//                url = "https://www.apache.org/licenses/LICENSE-2.0"
//                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
//            }
//        }
//        developers {
//            developer {
//                id = "bpappin"
//                name = "Brill pappin"
//                url = "https://github.com/bpappin"
//            }
//        }
//        scm {
//            url = "https://github.com/aughtone/aughtone-gravatar"
//            connection = "https://github.com/aughtone/aughtone-gravatar.git"
//            developerConnection = "git@github.com:aughtone/aughtone-gravatar.git"
//        }
//    }
//}
