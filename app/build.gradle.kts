
plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-parcelize")
    id ("com.google.devtools.ksp")
    id ("dagger.hilt.android.plugin")
//    id ("com.google.firebase.crashlytics")
}

android {
    namespace = "com.example.count_out"
    compileSdk = rootProject.extra["targetSdk"] as Int

    defaultConfig {
        applicationId = "com.example.count_out"
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["composeVersion"] as String
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    val composeVersion = rootProject.extra["composeVersion"] as String
    val daggerVersion = rootProject.extra["daggerVersion"] as String
    val roomVersion = "2.6.1"
    val lifecycleVersion = "2.6.2"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    //Hilt
    implementation ("com.google.dagger:hilt-android:$daggerVersion")
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0")
    ksp ("com.google.dagger:dagger-compiler:$daggerVersion")
    ksp ("com.google.dagger:hilt-compiler:$daggerVersion")
    testImplementation ("com.google.dagger:hilt-android-testing:$daggerVersion")
//    kaptTest ("com.google.dagger:hilt-android-compiler:$daggerVersion")
    //Jetpack  Compose
    implementation ("androidx.compose.ui:ui:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")
    //Tooling support (Previews, etc.)
    implementation ("androidx.compose.ui:ui-graphics:$composeVersion")
    implementation ("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    debugImplementation ("androidx.compose.ui:ui-tooling:$composeVersion")
    //Integration with observables
    implementation ("androidx.compose.runtime:runtime:$composeVersion")
    implementation ("androidx.compose.runtime:runtime-rxjava2:$composeVersion")
    //Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation ("androidx.compose.foundation:foundation:$composeVersion")
    implementation ("androidx.compose.foundation:foundation-layout:$composeVersion")
    // Material Design
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation ("androidx.compose.ui:ui-text-google-fonts:1.5.4")
    //Implementation ("androidx.compose.material3:material3:1.1.2")
    implementation ("androidx.compose.material:material-icons-core:$composeVersion")
    implementation ("androidx.compose.material:material-icons-extended:$composeVersion")
    //Adaptive
    implementation ("androidx.compose.material3:material3-window-size-class:1.1.2")
    //Navigation
    implementation ("androidx.navigation:navigation-compose:2.7.6")
    //Color Palette
    implementation ("androidx.palette:palette-ktx:1.0.0")
    //LifeCycle
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation ("androidx.fragment:fragment-ktx:1.6.2")
    // Room
    implementation ("androidx.room:room-runtime:$roomVersion")
    implementation ("androidx.room:room-ktx:$roomVersion")
    implementation ("androidx.room:room-rxjava2:$roomVersion")
    ksp ("androidx.room:room-compiler:$roomVersion")
    //Moshi
    implementation ("com.squareup.moshi:moshi:1.15.0")
    implementation ("com.squareup.moshi:moshi-kotlin:1.15.0")
    ksp ("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")
    // Import the Firebase BoM
//    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
//    implementation("com.google.firebase:firebase-analytics-ktx")
//    implementation("com.google.firebase:firebase-crashlytics")
//Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}