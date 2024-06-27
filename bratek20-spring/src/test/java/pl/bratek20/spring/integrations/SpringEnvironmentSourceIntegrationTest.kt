package pl.bratek20.spring.integrations

import com.github.bratek20.architecture.context.spring.PostProcessorForLegacyConfig
import com.github.bratek20.architecture.context.spring.SpringContextBuilder
import com.github.bratek20.architecture.context.spring.SpringContextBuilderProvider
import com.github.bratek20.architecture.properties.api.ListPropertyKey
import com.github.bratek20.architecture.properties.api.ObjectPropertyKey
import com.github.bratek20.architecture.properties.api.Properties
import com.github.bratek20.architecture.properties.context.PropertiesImpl
import com.github.bratek20.architecture.properties.sources.spring.SpringEnvironmentSource
import com.github.bratek20.architecture.properties.sources.spring.SpringEnvironmentSourceImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource
import pl.bratek20.spring.app.SpringApp


class TestBuilderProvider: SpringContextBuilderProvider {
    override fun provide(): SpringContextBuilder {
        return SpringContextBuilder()
            .withModules(
                PropertiesImpl(),
                SpringEnvironmentSourceImpl("my.properties")
            ) as SpringContextBuilder
    }
}

@Configuration
@Import(
    TestBuilderProvider::class,
    PostProcessorForLegacyConfig::class
)
open class TestConfig

data class MyProperty(
    val someInt: Int,
    val someString: String
)

class SpringEnvironmentSourceTest {
    @Test
    fun shouldReadPropertiesFromApplicationYaml() {
        val context = SpringApp.run(TestConfig::class.java, emptyArray())

        val properties = context.get(Properties::class.java)
        val env = context.get(ConfigurableEnvironment::class.java)
        val envSource = context.get(SpringEnvironmentSource::class.java)
        envSource.env = env

        env.propertySources.forEach { propertySource ->
            if (propertySource is MapPropertySource) {
                val map = propertySource.source
                map.forEach { (key: String, value: Any) ->
                    println("$key: $value")
                }
            }
        }

        assertThat(properties.get(ObjectPropertyKey("myProperty", MyProperty::class)))
            .isEqualTo(MyProperty(1, "a"))

        assertThat(properties.get(ListPropertyKey("myPropertyList", MyProperty::class)))
            .containsExactly(MyProperty(1, "a"), MyProperty(2, "b"))
    }
}