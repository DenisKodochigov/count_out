import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.count_out.presentation"
    compileSdk = 36
    buildToolsVersion = "35.0.0"

    defaultConfig {
        minSdk = 28
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release { isMinifyEnabled = false }
        debug { isMinifyEnabled = false }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    composeOptions { kotlinCompilerExtensionVersion = "2.2.21" }
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }
    packaging {
        resources.excludes.addAll(listOf("META-INF/LICENSE.md", "META-INF/LICENSE-notice.md", "/META-INF/{AL2.0,LGPL2.1}"))
    }
    kotlin {
        compilerOptions{ jvmTarget = JvmTarget.JVM_17 }
    }
    repositories {
        google()
        mavenLocal()
        mavenCentral()
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.bundles.core)
    implementation(libs.bundles.lifecycle)
    //Hilt
    implementation (libs.bundles.hilt)
    implementation(libs.compose.material)
    ksp (libs.bundles.hiltksp)
    //Permission
    implementation(libs.accompanist.permissions)
    //Jetpack  Compose
    implementation (libs.bundles.compose)
    implementation (platform(libs.compose.bom))
    debugImplementation (libs.compose.ui.tooling)
    // Material Design
    implementation (libs.bundles.material3)
    //LifeCycle
    implementation (libs.bundles.lifecycle)
//Testing
    testImplementation (libs.bundles.testImpl)
    androidTestImplementation (platform(libs.compose.bom))
    androidTestImplementation (libs.bundles.androidTestImpl)
}