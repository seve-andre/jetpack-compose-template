package com.mitch.template.util

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.AES
import dev.whyoleg.cryptography.algorithms.AES.Key.Size
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream

class CryptoManager(private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {
    private val provider = CryptographyProvider.Default
    private val aesGcm = provider.get(AES.GCM)
    private val keyGenerator = aesGcm.keyGenerator(Size.B256)
    private val key = keyGenerator.generateKeyBlocking()
    private val cipher = key.cipher()
    private val chunkSizeBytes = 4096

    suspend fun encrypt(bytes: ByteArray, outputStream: OutputStream) {
        withContext(ioDispatcher) {
            try {
                val ciphertext = cipher.encrypt(bytes)
                outputStream.use { it.write(ciphertext) }
            } catch (e: IOException) {
                ensureActive()
                throw e
            }
        }
    }

    suspend fun decrypt(inputStream: InputStream): ByteArray {
        return withContext(ioDispatcher) {
            try {
                val outputStream = ByteArrayOutputStream()
                val buffer = ByteArray(chunkSizeBytes)
                inputStream.use { stream ->
                    var bytesRead: Int
                    while (stream.read(buffer).also { bytesRead = it } != -1) {
                        ensureActive()
                        outputStream.write(buffer, 0, bytesRead)
                    }
                }
                cipher.decrypt(outputStream.toByteArray())
            } catch (e: IOException) {
                ensureActive()
                throw e
            }
        }
    }
}
