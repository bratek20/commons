package pl.bratek20.architecture.context.guice

import com.google.inject.AbstractModule
import com.google.inject.CreationException
import com.google.inject.Guice
import com.google.inject.multibindings.Multibinder
import pl.bratek20.architecture.context.api.Context
import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.DependentClassNotFoundInContextException
import pl.bratek20.architecture.context.impl.AbstractContextBuilder
import java.lang.reflect.Constructor

class MainModule(
    private val modules: List<AbstractModule>
): AbstractModule() {
    override fun configure() {
        binder().requireExplicitBindings();

        modules.forEach {
            install(it)
        }
    }
}

private fun <T> constructorToBind(type: Class<T>): Constructor<T> {
    val constructor = type.constructors.firstOrNull()
        ?: throw IllegalStateException("No constructors available for class ${type.name}")

    @Suppress("UNCHECKED_CAST")
    return constructor as Constructor<T>
}

class GuiceContextBuilder: AbstractContextBuilder() {
    private val modules = mutableListOf<AbstractModule>()

    override fun <T> setClass(type: Class<T>): ContextBuilder {
        modules.add(object: AbstractModule() {
            override fun configure() {
                bind(type).toConstructor(constructorToBind(type))
            }
        })
        return this
    }

    override fun <T> addClass(type: Class<T>): ContextBuilder {
        modules.add(object: AbstractModule() {
            override fun configure() {
                val multibinder = Multibinder.newSetBinder(binder(), type)
                multibinder.addBinding().toConstructor(constructorToBind(type))
            }
        })
        return this
    }

    override fun <I, T : I> setImpl(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder {
        modules.add(object: AbstractModule() {
            override fun configure() {
                bind(interfaceType).toConstructor(constructorToBind(implementationType))
            }
        })
        return this
    }

    override fun <I, T : I> addImpl(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder {
        modules.add(object: AbstractModule() {
            override fun configure() {
                val multibinder = Multibinder.newSetBinder(binder(), interfaceType)
                multibinder.addBinding().toConstructor(constructorToBind(implementationType))
            }
        })
        return this
    }

    override fun setObject(obj: Any): ContextBuilder {
        modules.add(object: AbstractModule() {
            override fun configure() {
                bind(obj::class.java as Class<Any>).toInstance(obj)
            }
        })
        return this
    }

    override fun addObject(obj: Any): ContextBuilder {
        modules.add(object: AbstractModule() {
            override fun configure() {
                val multibinder = Multibinder.newSetBinder(binder(), obj::class.java as Class<Any>)
                multibinder.addBinding().toInstance(obj)
            }
        })
        return this
    }

    override fun build(): Context {
        try {
            return GuiceContext(
                Guice.createInjector(MainModule(modules))
            )
        } catch (e: CreationException) {
            val msg = e.message ?: ""

            // Adjusted to handle multi-line and formatted error messages
            val lines = msg.split("\n")  // Split the message into lines
            val errorLine = lines.find { it.contains("[Guice/JitDisabled]: Explicit bindings are required") }

            requireNotNull(errorLine)

            val dependentClass = errorLine.substringAfter("Explicit bindings are required and ").substringBefore(" is not explicitly bound.")

            // Searching for the class with the error
            val errorDetailLine = lines.find { it.trim().startsWith("at") }?.trim() ?: ""
            val classWithError = errorDetailLine.substringAfter("at ").substringBefore(".<init>")

            throw DependentClassNotFoundInContextException("Class $dependentClass needed by class $classWithError not found")
        }
    }
}