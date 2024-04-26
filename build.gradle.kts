// Top-level build file where you can add configuration options common to all sub-projects/modules.
//buildscript {
//
//    val gradleVersion by extra("8.3.2")
//    val daggerVersion by extra("2.50")
//    val kotlinVersion by extra("1.9.23")
//
//    dependencies {
//        classpath ("com.google.dagger:hilt-android-gradle-plugin:$daggerVersion")
//        classpath ("com.android.tools.build:gradle:$gradleVersion")
//        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
//    }
//}

plugins {
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
//    alias(libs.plugins.firebase) apply false
}
tasks.register("clean", Delete::class) {  // #3
    delete(rootProject)
}