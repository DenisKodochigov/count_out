// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    val gradleVersion by extra("8.1.2")
    val daggerVersion by extra("2.48")
    val kotlinVersion by extra("1.9.10")
    val composeVersion by extra("1.5.4")
    val minSdk by extra(26)
    val targetSdk by extra(34)

//    repositories {
//        google()
//        mavenCentral()
//    }
    dependencies {
        classpath ("com.google.dagger:hilt-android-gradle-plugin:$daggerVersion")
        classpath ("com.android.tools.build:gradle:$gradleVersion")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

plugins {
//    val gradleVersion = extra["gradleVersion"]
//    val daggerVersion = extra["daggerVersion"]
    val gradleVersion = "8.1.2"
    val daggerVersion = "2.48"
    id ("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id ("com.diffplug.spotless") version "6.21.0"  apply false
    id ("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    id ("com.google.firebase.crashlytics") version "2.9.9" apply false
    id ("com.android.library") version gradleVersion apply false
    id ("com.android.application") version gradleVersion apply false
    id ("com.google.dagger.hilt.android") version daggerVersion apply false
}
tasks.register("clean", Delete::class) {  // #3
    delete(rootProject.buildDir)
}