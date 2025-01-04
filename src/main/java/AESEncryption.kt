import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import java.util.Base64

class AESEncryption {
    // Klucz AES
    private val secretKey: SecretKey

    init {
        // Generowanie klucza AES o długości 256 bitów
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256)
        secretKey = keyGen.generateKey()
    }

    // Funkcja szyfrowania
    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    // Funkcja deszyfrowania
    fun decrypt(data: String): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decodedBytes = Base64.getDecoder().decode(data)
        return String(cipher.doFinal(decodedBytes))
    }

    // Funkcja szyfrowania pliku
    fun encryptFile(inputFilePath: String, outputFilePath: String) {
        val inputFile = File(inputFilePath)
        val outputFile = File(outputFilePath)

        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        inputFile.inputStream().use { input ->
            outputFile.outputStream().use { output ->
                CipherOutputStream(output, cipher).use { cipherOut ->
                    input.copyTo(cipherOut)
                }
            }
        }
    }

    // Funkcja deszyfrowania pliku
    fun decryptFile(inputFilePath: String, outputFilePath: String) {
        val inputFile = File(inputFilePath)
        val outputFile = File(outputFilePath)

        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)

        inputFile.inputStream().use { input ->
            outputFile.outputStream().use { output ->
                CipherInputStream(input, cipher).use { cipherIn ->
                    cipherIn.copyTo(output)
                }
            }
        }
    }
}

