package com.github.bratek20.architecture.serialization.api

import com.github.bratek20.architecture.exceptions.ApiException

class DeserializationException(message: String): ApiException(message)

interface Serializer {
    fun serialize(value: Any): SerializedValue

    @Throws(DeserializationException::class)
    fun <T> deserialize(serializedValue: SerializedValue, type: Class<T>): T
}