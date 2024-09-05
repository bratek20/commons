package com.github.bratek20.architecture.properties.impl

import com.github.bratek20.architecture.properties.api.*
import com.github.bratek20.architecture.serialization.api.SerializedValue
import com.github.bratek20.architecture.storage.api.NotFoundInStorageException
import com.github.bratek20.architecture.storage.api.StorageKeyTypeException
import com.github.bratek20.architecture.storage.impl.StorageLogic

class PropertiesLogic(
    private val initialSources: Set<PropertiesSource>
) : Properties, StorageLogic() {
    private val addedSources = mutableListOf<PropertiesSource>()

    private val allSources: Set<PropertiesSource>
        get() = initialSources + addedSources

    override fun <T : Any> get(key: PropertyKey<T>): T {
        findSourceWithKeyName(key.name)
            ?: throw PropertyNotFoundException("Property `${key.name}` not found, sources: ${allSources.map { it.getName().value }}")

        return super.get(key)
    }

    override fun <Id : Any, E : Any> findElement(key: MapPropertyKey<Id, E>, id: Id): E? {
        val list = get(key)
        return list.firstOrNull { key.idProvider(it) == id }
    }

    override fun addSource(source: PropertiesSource) {
        addedSources.add(source)
    }

    private fun findSourceWithKeyName(keyName: String): PropertiesSource? {
        return allSources.firstOrNull {
            it.getAllKeys().contains(keyName)
        }
    }

    override fun findValue(keyName: String): SerializedValue? {
        val source = findSourceWithKeyName(keyName)
        return source?.getValue(keyName)
    }

    override fun storageElementName(): String {
        return "Property"
    }

    override fun notFoundException(message: String): NotFoundInStorageException {
        return PropertyNotFoundException(message)
    }

    override fun keyTypeException(message: String): StorageKeyTypeException {
        return PropertyKeyTypeException(message)
    }
}
