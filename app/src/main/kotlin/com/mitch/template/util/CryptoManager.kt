package com.mitch.template.util

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class CryptoManager {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private val encryptCipher
        get() = Cipher.getInstance(AesGcmNoPaddingTransformation).apply {
            init(Cipher.ENCRYPT_MODE, getKey())
        }

    private fun getDecryptCipherForIv(iv: ByteArray): Cipher {
        return Cipher.getInstance(AesGcmNoPaddingTransformation).apply {
            init(Cipher.DECRYPT_MODE, getKey(), GCMParameterSpec(GcmTlen, iv))
        }
    }

    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(KeyAlias, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        return KeyGenerator.getInstance(AesAlgorithm).apply {
            init(
                KeyGenParameterSpec.Builder(
                    KeyAlias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(GcmBlockMode)
                    .setEncryptionPaddings(PaddingNone)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    fun encrypt(bytes: ByteArray, outputStream: OutputStream) {
        val cipher = encryptCipher
        val iv = cipher.iv
        outputStream.use {
            it.write(iv)
            val inputStream = ByteArrayInputStream(bytes)
            val buffer = ByteArray(ChunkSize)
            while (inputStream.available() > ChunkSize) {
                inputStream.read(buffer)
                val ciphertextChunk = cipher.update(buffer)
                it.write(ciphertextChunk)
            }
            // the last chunk must be written using doFinal() because this takes the padding into account
            val remainingBytes = inputStream.readBytes()
            val lastChunk = cipher.doFinal(remainingBytes)
            it.write(lastChunk)
        }
    }

    fun decrypt(inputStream: InputStream): ByteArray {
        return inputStream.use {
            val iv = ByteArray(GcmIvSize)
            it.read(iv)
            val cipher = getDecryptCipherForIv(iv)
            val outputStream = ByteArrayOutputStream()

            val buffer = ByteArray(ChunkSize)
            while (inputStream.available() > ChunkSize) {
                inputStream.read(buffer)
                val ciphertextChunk = cipher.update(buffer)
                outputStream.write(ciphertextChunk)
            }
            // the last chunk must be read using doFinal() because this takes the padding into account
            val remainingBytes = inputStream.readBytes()
            val lastChunk = cipher.doFinal(remainingBytes)
            outputStream.write(lastChunk)

            outputStream.toByteArray()
        }
    }

    private companion object {
        private const val KeyAlias = "keystore_key"
        private const val ChunkSize = 4096
        private const val GcmTlen = 128
        private const val GcmIvSize = 12
        private const val AesAlgorithm = KeyProperties.KEY_ALGORITHM_AES
        private const val GcmBlockMode = KeyProperties.BLOCK_MODE_GCM
        private const val PaddingNone = KeyProperties.ENCRYPTION_PADDING_NONE
        private const val AesGcmNoPaddingTransformation = "$AesAlgorithm/$GcmBlockMode/$PaddingNone"
    }
}
