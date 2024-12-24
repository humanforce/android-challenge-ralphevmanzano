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

rootProject.name = "Humanforce Android Engineering Challenge"
include(":app")
include(":core:data")
include(":core:database")
include(":core:designsystem")
include(":core:network")
include(":core:domain")
include(":feature:home")
include(":feature:cities")
include(":feature:search")
