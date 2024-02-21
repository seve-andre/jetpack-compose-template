package com.mitch.appname.data.userprefs

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.mitch.appname.domain.models.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
            withContext(Dispatchers.IO) {
                ProtoBuf.decodeFromByteArray<UserPreferences>(bytes = input.readBytes())
            }
        } catch (exception: SerializationException) {
            throw CorruptionException("Cannot read proto. $exception")
        }
    }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(ProtoBuf.encodeToByteArray(t))
        }
    }
}
