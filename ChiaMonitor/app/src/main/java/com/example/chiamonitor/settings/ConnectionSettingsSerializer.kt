package com.example.chiamonitor.settings

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object ConnectionSettingsSerializer : Serializer<ConnectionSettings> {
    override val defaultValue: ConnectionSettings
        get() = ConnectionSettings()

    override suspend fun readFrom(input: InputStream): ConnectionSettings {
        return try {
            Json.decodeFromString(
                deserializer =  ConnectionSettings.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch(e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: ConnectionSettings, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = ConnectionSettings.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}