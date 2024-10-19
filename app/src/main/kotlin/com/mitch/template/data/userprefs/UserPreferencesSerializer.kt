package com.mitch.template.data.userprefs

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import java.io.InputStream
import java.io.OutputStream

/**
 * [UserPreferencesSerializer] is used to serialize/deserialize from Proto Datastore,
 * in particular the [ProtoUserPreferences] datastore.
 *
 * see more at [Proto Datastore](https://developer.android.com/topic/libraries/architecture/datastore#proto-create)
 */
object UserPreferencesSerializer : Serializer<ProtoUserPreferences> {
    override val defaultValue: ProtoUserPreferences = ProtoUserPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ProtoUserPreferences {
        return try {
            // readFrom is already called on the data store background thread
            ProtoUserPreferences.parseFrom(input)
        } catch (exception: SerializationException) {
            throw CorruptionException("Cannot read proto. $exception")
        }
    }

    override suspend fun writeTo(t: ProtoUserPreferences, output: OutputStream) {
        // writeTo is already called on the data store background thread
        t.writeTo(output)
    }
}
