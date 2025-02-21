pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "app"
include(":app")
include(":domain")
include(":device")
include(":data")
include(":framework")
