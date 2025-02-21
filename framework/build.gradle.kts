plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.count_out.framework"
    compileSdk = 35

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}
dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(libs.bundles.core)
    //Hilt
    implementation (libs.bundles.hilt)
    ksp (libs.bundles.hiltksp)
    // Room
    implementation (libs.bundles.room)
    ksp (libs.room.compiler)
    // Retrofit
    implementation (libs.bundles.retrofit)
    //Moshi
    implementation (libs.bundles.moshi)
    ksp (libs.moshi.kotlin.codegen)
    //DataStore
    implementation(libs.bundles.datastore)

    debugImplementation(libs.ui.test.manifest)
    testImplementation (libs.bundles.testing)
    androidTestImplementation (platform(libs.compose.bom))
    androidTestImplementation (libs.bundles.testingAndroid)
}