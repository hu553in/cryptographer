package com.github.hu553in.cryptographer.ciphers

import com.github.hu553in.cryptographer.exceptions.NullCipherContextParamException
import com.github.hu553in.cryptographer.utils.CipherContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AffineTest {

    @Test
    fun testBasicEncryption() {
        val ctx = CipherContext(b = 3)
        val encrypted = Affine.encrypt("HELLO", ctx)
        Assertions.assertEquals("YPKKT", encrypted)
    }

    @Test
    fun testBasicDecryption() {
        val ctx = CipherContext(b = 3)
        val decrypted = Affine.decrypt("YPKKT", ctx)
        Assertions.assertEquals("HELLO", decrypted)
    }

    @Test
    fun testRoundTrip() {
        val ctx = CipherContext(b = 5)
        val original = "CRYPTOGRAPHY"
        val encrypted = Affine.encrypt(original, ctx)
        val decrypted = Affine.decrypt(encrypted, ctx)
        Assertions.assertEquals(original, decrypted)
    }

    @Test
    fun testNonAlphabetCharacters() {
        val ctx = CipherContext(b = 7)
        val message = "HELLO 123!"
        val encrypted = Affine.encrypt(message, ctx)
        Assertions.assertEquals("CTOOX 123!", encrypted)
    }

    @Test
    fun testNullBParameter() {
        Assertions.assertThrows(NullCipherContextParamException::class.java) {
            Affine.encrypt("TEST", CipherContext())
        }
    }

    @Test
    fun testEmptyString() {
        val ctx = CipherContext(b = 9)
        Assertions.assertEquals("", Affine.encrypt("", ctx))
        Assertions.assertEquals("", Affine.decrypt("", ctx))
    }
}
