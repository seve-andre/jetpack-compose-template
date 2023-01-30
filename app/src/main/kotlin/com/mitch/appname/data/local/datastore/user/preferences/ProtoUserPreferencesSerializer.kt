package com.mitch.appname.data.local.datastore.user.preferences

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.mitch.appname.ProtoUserPreferences
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class ProtoUserPreferencesSerializer @Inject constructor() : Serializer<ProtoUserPreferences> {
    override val defaultValue: ProtoUserPreferences = ProtoUserPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ProtoUserPreferences =
        try {
            ProtoUserPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: ProtoUserPreferences, output: OutputStream) = t.writeTo(output)
}
