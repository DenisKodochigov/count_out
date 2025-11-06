import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.count_out.framework"
    compileSdk = 36
    defaultConfig { minSdk = 28  }
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }
    buildTypes {
        release { isMinifyEnabled = true }
        debug { isMinifyEnabled = false }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin { compilerOptions{ jvmTarget = JvmTarget.JVM_17 } }
}
//val mockitoAgent = configurations.create("mockitoAgent")
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
    ksp (libs.moshi.ksp)
    //DataStore
    implementation(libs.bundles.datastore)
    //Location
    implementation(libs.bundles.gms)

    debugImplementation(libs.ui.test.manifest)
    testImplementation (libs.bundles.testImpl)
//    androidTestImplementation (platform(libs.compose.bom))
    androidTestImplementation (libs.bundles.androidTestImpl)
//    mockitoAgent(libs.moskito.core){ isTransitive = false }
    testRuntimeOnly(libs.jupiter.engine)
}

//tasks.withType<Test>{jvmArgs("-javaagent:${mockitoAgent.asPath}")}
