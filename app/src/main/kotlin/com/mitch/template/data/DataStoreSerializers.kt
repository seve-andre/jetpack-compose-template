package com.mitch.template.data

import androidx.datastore.core.Serializer
import com.mitch.template.util.CryptoManager
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream

fun <T> Serializer<T>.encrypted(): Serializer<T> = EncryptedSerializer(this)
class EncryptedSerializer<T>(private val wrappedSerializer: Serializer<T>) : Serializer<T> {
    override val defaultValue: T = wrappedSerializer.defaultValue

    override suspend fun readFrom(input: InputStream): T {
        val decryptedBytes = CryptoManager.decrypt(input)
        return wrappedSerializer.readFrom(decryptedBytes.inputStream())
    }

    override suspend fun writeTo(t: T, output: OutputStream) {
        val bytes = ByteArrayOutputStream().use { stream ->
            wrappedSerializer.writeTo(t, stream)
            stream.toByteArray()
        }
        CryptoManager.encrypt(bytes, output)
    }
}
