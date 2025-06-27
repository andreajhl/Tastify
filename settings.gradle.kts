pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "App - Android"
include(":app")
include(":library:utils")
include(":data")
include(":core:session")
include(":core:model")
include(":feature:login")
include(":feature:common")
include(":feature:register")
include(":feature:cart")
include(":feature:productList")
include(":feature:navbar")
include(":feature:orchestrator")
include(":feature:productFilter")
include(":core:theme")
include(":feature:profile")
