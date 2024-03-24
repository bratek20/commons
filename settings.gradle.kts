pluginManagement {
    includeBuild("version-catalog")
    includeBuild("conventions")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from("pl.bratek20:version-catalog:1.0.0-SNAPSHOT")
        }
    }

    repositories {
        mavenLocal()

        if (System.getenv("GITHUB_ACTOR") != null) {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/bratek20/starter")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}

rootProject.name = "bratek20-starter"

include("bratek20-spring")
include("bratek20-tests")
include("bratek20-architecture")
include("bratek20-commons")