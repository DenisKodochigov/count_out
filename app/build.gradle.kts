
plugins {
//    id ("kotlin-parcelize")
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
//    alias(libs.plugins.firebase)
    }

android {
    namespace = "com.example.count_out"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.count_out"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            // Enables code shrinking, obfuscation, and optimization for only
            // your project's release build type. Make sure to use a build
            // variant with `isDebuggable=false`.
            isMinifyEnabled = false
            // Enables resource shrinking, which is performed by the
            // Android Gradle plugin.
//            isShrinkResources = true
            // Includes the default ProGuard rules files that are packaged with
            // the Android Gradle plugin. To learn more, go to the section about
            // R8 configuration files.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12"
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    //Hilt
    implementation (libs.hilt.android)
    implementation (libs.androidx.hilt.navigation.compose)
    implementation(libs.play.services.location)
    ksp (libs.dagger.compiler)
    ksp (libs.hilt.compiler)
    testImplementation (libs.hilt.android.testing)
    //Jetpack  Compose
    implementation (libs.androidx.ui)
    implementation(libs.accompanist.permissions)
    //Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation (libs.androidx.foundation)
    implementation (libs.androidx.foundation.layout)
    //Navigation
    implementation (libs.androidx.navigation.compose)
    //Tooling support (Previews, etc.)
    implementation (libs.androidx.ui.graphics)
    implementation (libs.androidx.ui.tooling.preview)
    debugImplementation (libs.androidx.ui.tooling)
    //Integration with observables
    implementation (libs.androidx.runtime)
    // Material Design
    implementation (libs.androidx.material3)
    implementation (libs.androidx.ui.text.google.fonts)
    implementation (libs.androidx.material.icons.core)
    implementation (libs.androidx.material.icons.extended)
    //Color Palette
    implementation (libs.androidx.palette.ktx)
    //LifeCycle
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation (libs.androidx.fragment.ktx)
    // Room
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    ksp (libs.androidx.room.compiler)
    //Moshi
    implementation (libs.moshi)
    implementation (libs.moshi.kotlin)
    ksp (libs.moshi.kotlin.codegen)

    debugImplementation(libs.androidx.ui.test.manifest)
//Testing
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.androidx.espresso.core)
    androidTestImplementation (platform(libs.androidx.compose.bom))
    androidTestImplementation (libs.androidx.ui.test.junit4)
}