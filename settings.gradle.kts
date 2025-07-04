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

rootProject.name = "fastity"

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
include(":feature:orchestrator")
include(":feature:productFilter")
include(":feature:navigation")
include(":core:theme")
include(":feature:profile")
include(":feature:menu")
include(":feature:orderHistory")
include(":feature:home")
include(":core:db")
include(":core:network")
