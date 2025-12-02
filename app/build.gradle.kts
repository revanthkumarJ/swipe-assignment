plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.revanth.swipe"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.revanth.swipe"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // ───────────────────────────────────────────────
    // Core Android / Kotlin
    // ───────────────────────────────────────────────
    implementation(libs.core.ktx.v1131)
    implementation(libs.androidx.lifecycle.runtime.ktx.v287)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // ───────────────────────────────────────────────
    // Navigation
    // ───────────────────────────────────────────────
    implementation(libs.androidx.navigation.compose)

    // ───────────────────────────────────────────────
    // Dependency Injection (Koin)
    // ───────────────────────────────────────────────
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation(libs.koin.androidx.compose)

    // ───────────────────────────────────────────────
    // Retrofit + Kotlin Serialization
    // ───────────────────────────────────────────────
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization.json)

    // OkHttp (recommended for Retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // ───────────────────────────────────────────────
    // Room Database
    // ───────────────────────────────────────────────
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp("androidx.room:room-compiler:2.6.1")

    // ───────────────────────────────────────────────
    // Coil Image Loading
    // ───────────────────────────────────────────────
    implementation(libs.coil.compose)

    // ───────────────────────────────────────────────
    // Compose Navigation & Material
    // Already added via BOM but adding for completeness
    // ───────────────────────────────────────────────
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material.icons.extended)

    // ───────────────────────────────────────────────
    // Splash Screen API
    // ───────────────────────────────────────────────
    implementation(libs.androidx.core.splashscreen)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)


}