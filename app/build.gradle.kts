import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}
android {
    namespace = "com.count_out.app"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.count_out"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
    }
    buildTypes {
        debug { isMinifyEnabled = false }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures { compose = true }
    packaging { resources.excludes.addAll(
        listOf("META-INF/LICENSE.md", "META-INF/LICENSE-notice.md", "/META-INF/{AL2.0,LGPL2.1}")) }
    composeOptions { kotlinCompilerExtensionVersion = libs.versions.kotlin.get() }
    kotlin { compilerOptions{ jvmTarget = JvmTarget.JVM_17 } }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":device"))
    implementation(project(":domain"))
    implementation(project(":service"))
    implementation(project(":framework"))
    implementation(project(":presentation"))
    implementation(libs.bundles.core)
    implementation(libs.bundles.lifecycle)
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
    debugImplementation(libs.bundles.debug)
//Testing
    testImplementation (libs.bundles.testImpl)
    androidTestImplementation (platform(libs.compose.bom))
    androidTestImplementation (libs.bundles.androidTestImpl)
}