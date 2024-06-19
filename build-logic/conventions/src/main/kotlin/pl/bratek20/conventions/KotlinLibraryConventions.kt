package pl.bratek20.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaTestFixturesPlugin
import pl.bratek20.extensions.versionCatalog

class KotlinLibraryConventions: Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(KotlinConventions::class.java)

                apply(JavaLibraryPlugin::class.java)

                apply(PublishConventions::class.java)

                apply(JavaTestFixturesPlugin::class.java)
            }

            with(dependencies) {
                // assertions
                add("testFixturesImplementation", versionCatalog().findLibrary("kotest-assertions-core").get())
            }
        }
    }
}