package com.mitch.template.core.datastore.userprefs

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.mitch.template.core.domain.models.UserPreferences
import com.mitch.template.core.util.di.Dispatcher
import com.mitch.template.core.util.di.TemplateDispatcher.Io
import kotlinx.coroutines.CoroutineDispatcher
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
class UserPreferencesSerializer @Inject constructor(
    @Dispatcher(Io) private val ioDispatcher: CoroutineDispatcher
) : Serializer<UserPreferences> {
    override val defaultValue: UserPreferences = UserPreferences()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        return try {
            withContext(ioDispatcher) {
                ProtoBuf.decodeFromByteArray<UserPreferences>(bytes = input.use { it.readBytes() })
            }
        } catch (exception: SerializationException) {
            throw CorruptionException("Cannot read proto. $exception")
        }
    }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        withContext(ioDispatcher) {
            output.write(ProtoBuf.encodeToByteArray(t))
        }
    }
}
