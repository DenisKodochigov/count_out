pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "count_out"
include(":app")
include(":domain")
include(":device")
include(":data")
include(":framework")
