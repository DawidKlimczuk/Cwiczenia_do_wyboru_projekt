package test.encryption

import main.encryption.AESEncryption
import kotlin.test.Test
import kotlin.test.assertEquals

class AESEncryptionTest {
    @Test
    fun testEncryptionDecryption() {
        val encryption = AESEncryption()
        val message = "Test Message"
        val encryptedMessage = encryption.encrypt(message)
        val decryptedMessage = encryption.decrypt(encryptedMessage)

        assertEquals(message, decryptedMessage)
    }
}
