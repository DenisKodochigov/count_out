import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.count_out.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 28
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    repositories {
        google()
        mavenLocal()
        mavenCentral()
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }
    buildToolsVersion = "35.0.0"
    kotlin { compilerOptions{ jvmTarget = JvmTarget.JVM_17 } }
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.core.ktx)
    implementation (libs.bundles.hilt)
    ksp (libs.bundles.hiltksp)

    debugImplementation(libs.ui.test.manifest)
    testImplementation (libs.bundles.testImpl)
    androidTestImplementation (platform(libs.compose.bom))
    androidTestImplementation (libs.bundles.androidTestImpl)
}