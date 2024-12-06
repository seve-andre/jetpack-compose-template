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
        get() = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, getKey())
        }

    private fun getDecryptCipherForIv(iv: ByteArray): Cipher {
        return Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE, getKey(), GCMParameterSpec(TLEN, iv))
        }
    }

    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
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
            val buffer = ByteArray(CHUNK_SIZE)
            while (inputStream.available() > CHUNK_SIZE) {
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
            val iv = ByteArray(IV_SIZE)
            it.read(iv)
            val cipher = getDecryptCipherForIv(iv)
            val outputStream = ByteArrayOutputStream()

            val buffer = ByteArray(CHUNK_SIZE)
            while (inputStream.available() > CHUNK_SIZE) {
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

    companion object {
        private const val KEY_ALIAS = "keystore_key"
        private const val CHUNK_SIZE = 4096
        private const val TLEN = 128
        private const val IV_SIZE = 12
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }
}
