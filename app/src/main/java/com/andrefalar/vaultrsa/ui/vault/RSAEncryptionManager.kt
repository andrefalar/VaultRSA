package com.andrefalar.vaultrsa.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher

class RSAEncryptionManager {

    // Alias que usaremos para identificar nuestras claves en el Android KeyStore
    private val keyAlias = "VaultRSAKeyAlias"

    // Accedemos al KeyStore de Android, un almacén seguro de claves
    private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    // Bloque init que se ejecuta cuando se instancia la clase. Aquí verificamos si necesitamos generar un par de claves.
    init {
        generateKeyPairIfNeeded()
    }

    // Método que genera un par de claves (pública y privada) si no existe ya en el KeyStore
    private fun generateKeyPairIfNeeded() {
        // Comprobamos si ya existe una clave con el alias especificado en el KeyStore
        if (!keyStore.containsAlias(keyAlias)) {
            // Obtenemos un generador de claves RSA que trabaja con el AndroidKeyStore
            val keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore"
            )

            // Configuramos las propiedades del generador de claves, como el propósito y el padding
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                keyAlias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .setKeySize(2048)  // Tamaño de clave recomendado para seguridad (2048 bits)
                .build()

            // Inicializamos el generador de claves con los parámetros anteriores y generamos las claves
            keyPairGenerator.initialize(keyGenParameterSpec)
            keyPairGenerator.generateKeyPair()
        }
    }

    // Método para obtener la clave pública del KeyStore
    private fun getPublicKey(): PublicKey? {
        // Obtenemos la entrada del KeyStore con nuestro alias y extraemos la clave pública del certificado
        return (keyStore.getEntry(keyAlias, null) as? KeyStore.PrivateKeyEntry)?.certificate?.publicKey
    }

    // Método para obtener la clave privada del KeyStore
    private fun getPrivateKey(): PrivateKey? {
        // Obtenemos la clave privada almacenada en el KeyStore bajo nuestro alias
        return (keyStore.getEntry(keyAlias, null) as? KeyStore.PrivateKeyEntry)?.privateKey
    }

    // Cifrado de datos usando la clave pública
    fun encryptData(data: String): ByteArray {
        // Instanciamos el cifrador RSA con el padding adecuado
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        // Inicializamos el cifrador en modo de cifrado con la clave pública
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey())
        // Ciframos los datos y los devolvemos como un array de bytes
        return cipher.doFinal(data.toByteArray())
    }

    // Descifrado de datos usando la clave privada
    fun decryptData(encryptedData: ByteArray): String {
        // Instanciamos el cifrador RSA con el padding adecuado
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        // Inicializamos el cifrador en modo de descifrado con la clave privada
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey())
        // Desciframos los datos y los convertimos de nuevo a String
        return String(cipher.doFinal(encryptedData))
    }
}
