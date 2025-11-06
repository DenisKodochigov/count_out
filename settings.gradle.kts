pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    // Запрещаем использовать локальные репозитории в модулях
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "count_out"
include(":app")
include(":domain")
include(":device")
include(":data")
include(":framework")
include(":service")
include(":presentation")
