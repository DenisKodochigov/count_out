import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.mannodermaus)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.count_out.device"
    compileSdk = 36

    defaultConfig {
        minSdk = 28
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles("consumer-rules.pro")
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
    kotlin { compilerOptions{
        jvmTarget = JvmTarget.JVM_17
        freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    } }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(libs.bundles.core)
    //Hilt
    implementation (libs.bundles.hilt)
    ksp (libs.bundles.hiltksp)
    //Location
    implementation(libs.bundles.gms)

    debugImplementation(libs.ui.test.manifest)
    testImplementation (libs.bundles.testImpl)
    androidTestImplementation (platform(libs.compose.bom))
    androidTestImplementation (libs.bundles.androidTestImpl)
}