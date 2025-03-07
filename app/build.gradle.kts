plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
//    id ("kotlin-parcelize")
//    alias(libs.plugins.firebase)
    }

android {
    namespace = "com.example.count_out"
    compileSdk = 35
    buildToolsVersion = "35.0.0"

    defaultConfig {
        applicationId = "com.example.count_out"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    repositories {
        google()
        mavenLocal()
        mavenCentral()
    }
    buildTypes {
        release {
            // Enables code shrinking, obfuscation, and optimization for only
            // your project's release build type. Make sure to use a build
            // variant with `isDebuggable=false`.
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            versionNameSuffix = ".dev"
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
        buildConfig = true
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(libs.bundles.core)
    //Hilt
    implementation (libs.bundles.hilt)
    ksp (libs.bundles.hiltksp)
    //Location
    implementation(libs.bundles.gms)
    //Permission
    implementation(libs.accompanist.permissions)
    //Jetpack  Compose
    implementation (libs.bundles.compose)
    implementation (platform(libs.compose.bom))
    debugImplementation (libs.compose.ui.tooling)
    //Navigation
    implementation (libs.navigation.compose)
    // Material Design
    implementation (libs.bundles.material3)
    //LifeCycle
    implementation (libs.bundles.lifecycle)
    // Room
    implementation (libs.bundles.room)
    ksp (libs.room.compiler)
    // Retrofit
    implementation (libs.bundles.retrofit)
    //Moshi
    implementation (libs.bundles.moshi)
    ksp (libs.moshi.ksp)
    //DataStore
    implementation(libs.datastore)
    debugImplementation(libs.ui.test.manifest)
//Testing
    testImplementation (libs.bundles.testing)
    androidTestImplementation (platform(libs.compose.bom))
    androidTestImplementation (libs.bundles.testingAndroid)
}