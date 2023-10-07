plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
}

android {
    namespace = "com.mustfaibra.qpaymultiplatform.android"
    compileSdk = 33
    packagingOptions {
        resources.excludes.add("META-INF/versions/9/previous-compilation-data.bin")
    }
    defaultConfig {
        applicationId = "com.mustfaibra.qpaymultiplatform.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        configurations.all {
            resolutionStrategy {
                force("androidx.emoji2:emoji2-views-helper:1.3.0")
                force("androidx.emoji2:emoji2:1.3.0")
            }
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.4.0")
    implementation("androidx.compose.ui:ui-tooling:1.4.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0")
    implementation("androidx.compose.foundation:foundation:1.4.0")
    implementation("androidx.compose.material:material:1.4.0")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation("io.insert-koin:koin-android:3.4.0")
}
