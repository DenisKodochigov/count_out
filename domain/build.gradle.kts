import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.count_out.domain"
    compileSdk = 36
    defaultConfig { minSdk = 28 }
    buildTypes {
        release { isMinifyEnabled = true }
        debug { isMinifyEnabled = false }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }
    kotlin { compilerOptions{ jvmTarget = JvmTarget.JVM_17} }
}

dependencies {
    implementation(libs.core.ktx)
    //Hilt
    implementation (libs.bundles.hilt)
    ksp (libs.bundles.hiltksp)

    testImplementation (libs.bundles.testImpl)
//    androidTestImplementation (platform(libs.compose.bom))
    androidTestImplementation (libs.bundles.androidTestImpl)
}