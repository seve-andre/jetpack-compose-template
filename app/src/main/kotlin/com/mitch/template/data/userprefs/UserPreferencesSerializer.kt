package com.mitch.template.data.userprefs

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.mitch.template.domain.models.UserPreferences
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

@OptIn(ExperimentalSerializationApi::class)
class UserPreferencesSerializer @Inject constructor() : Serializer<UserPreferences> {
    override val defaultValue: UserPreferences = UserPreferences()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        return try {
            // readFrom is already called on the data store background thread
            ProtoBuf.decodeFromByteArray<UserPreferences>(bytes = input.readBytes())
        } catch (exception: SerializationException) {
            throw CorruptionException("Cannot read proto. $exception")
        }
    }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        // writeTo is already called on the data store background thread
        output.write(ProtoBuf.encodeToByteArray(t))
    }
}
