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
include(":data")
include(":core:db")
include(":core:theme")
include(":core:session")
include(":core:network")
include(":library:utils")
include(":feature:home")
include(":feature:cart")
include(":feature:menu")
include(":feature:login")
include(":feature:common")
include(":feature:profile")
include(":feature:register")
include(":feature:orderPay")
include(":feature:productList")
include(":feature:orderHistory")
include(":feature:productFilter")
include(":feature:orderDetail")
include(":feature:authentication")
include(":core:navigation")
