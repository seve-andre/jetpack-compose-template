package com.mitch.template.data

import androidx.datastore.core.Serializer
import com.mitch.template.util.CryptoManager
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream

fun <T> Serializer<T>.encrypted(): Serializer<T> = EncryptedSerializer(this)

@Suppress("TooGenericExceptionCaught")
class EncryptedSerializer<T>(
    private val wrappedSerializer: Serializer<T>,
    private val cryptoManager: CryptoManager = CryptoManager()
) : Serializer<T> {
    override val defaultValue: T = wrappedSerializer.defaultValue

    override suspend fun readFrom(input: InputStream): T {
        val decryptedBytes = cryptoManager.decrypt(input)
        return try {
            wrappedSerializer.readFrom(decryptedBytes.inputStream())
        } catch (e: Exception) {
            Timber.e("Error reading from encrypted datastore: ${e.message}")
            defaultValue
        }
    }

    override suspend fun writeTo(t: T, output: OutputStream) {
        val bytes = ByteArrayOutputStream().use { stream ->
            wrappedSerializer.writeTo(t, stream)
            stream.toByteArray()
        }
        cryptoManager.encrypt(bytes, output)
    }
}
