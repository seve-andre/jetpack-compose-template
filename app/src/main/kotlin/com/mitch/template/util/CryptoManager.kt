package com.mitch.template.util

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.AES
import dev.whyoleg.cryptography.algorithms.AES.Key.Size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream

object CryptoManager {
    private val provider = CryptographyProvider.Default
    private val aesGcm = provider.get(AES.GCM)
    private val keyGenerator = aesGcm.keyGenerator(Size.B256)
    private val key: AES.GCM.Key = keyGenerator.generateKeyBlocking()
    private val cipher = key.cipher()

    suspend fun encrypt(bytes: ByteArray, outputStream: OutputStream) {
        withContext(Dispatchers.IO) {
            try {
                val ciphertext: ByteArray = cipher.encrypt(bytes)
                outputStream.use { it.write(ciphertext) }
            } catch (e: Exception) {
                coroutineContext.ensureActive()
                throw e
            }
        }
    }

    suspend fun decrypt(inputStream: InputStream): ByteArray {
        return withContext(Dispatchers.IO) {
            try {
                val outputStream = ByteArrayOutputStream()
                val buffer = ByteArray(4096)
                inputStream.use { stream ->
                    var bytesRead: Int
                    while (stream.read(buffer).also { bytesRead = it } != -1) {
                        ensureActive()
                        outputStream.write(buffer, 0, bytesRead)
                    }
                }
                cipher.decrypt(outputStream.toByteArray())
            } catch (e: Exception) {
                ensureActive()
                throw e
            }
        }
    }
}
