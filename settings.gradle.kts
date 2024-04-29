pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Chat Buddy"
include(":app")
include(":login")
include(":chat:chat_data")
include(":chat:chat_domain")
include(":chat:chat_presentation")
include(":core:ui")
include(":core:utils")
include(":core:di")
