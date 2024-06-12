package com.example.passwordmanagerapp

import android.annotation.SuppressLint
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.InputStream
import java.io.OutputStream
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class CryptoManager {
    private  val  keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private var encryptCipher = Cipher.getInstance(TRANSFORMATION).apply {
        init(Cipher.ENCRYPT_MODE,getKey())
    }


    private fun getDecryptCipherForIv(iv:ByteArray):Cipher{
        return Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE,getKey(), IvParameterSpec(iv))
        }
    }




    private fun getKey():SecretKey{
        val existingKey = keyStore.getEntry("secret",null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }


    /**
     * Function to create the cipher key which will be used in the encryption and decryption
     */
    private fun createKey(): SecretKey{
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    "secret",
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

    /**
     * function to encrypt the password into ByteArray using AES algorithm
     */
    fun encrypt(bytes:ByteArray, ):ByteArray{
        val iv = encryptCipher.iv
        // Encrypt the input bytes
        val encryptedBytes = encryptCipher.doFinal(bytes)
        // Return the IV followed by the encrypted bytes
        return iv + encryptedBytes
    }

    /**
     * function to decrypt the encrypted Byte into ByteArray
     */
    fun decrypt(encryptedBytes: ByteArray):ByteArray{
        val iv = encryptedBytes.take(16).toByteArray()
        // assuming IV size is 16 bytes
        // Extract the actual encrypted bytes
        val actualEncryptedBytes = encryptedBytes.drop(16).toByteArray()
        return getDecryptCipherForIv(iv).doFinal(actualEncryptedBytes)

    }




    companion object{
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE =KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }
}