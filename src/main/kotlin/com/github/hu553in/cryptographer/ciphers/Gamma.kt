package com.github.hu553in.cryptographer.ciphers

import com.github.hu553in.cryptographer.exceptions.NullCipherContextParamException
import com.github.hu553in.cryptographer.rng.LinearCongruentGenerator
import com.github.hu553in.cryptographer.roles.Decrypter
import com.github.hu553in.cryptographer.roles.Encryptor
import com.github.hu553in.cryptographer.utils.ALPHABET_SIZE
import com.github.hu553in.cryptographer.utils.A_CODE
import com.github.hu553in.cryptographer.utils.A_CODE_Z_CODE_RANGE
import com.github.hu553in.cryptographer.utils.CipherContext
import kotlin.math.abs

object Gamma : Encryptor, Decrypter {
    override fun encrypt(source: String, ctx: CipherContext): String {
        val startKey = ctx.startKey ?: throw NullCipherContextParamException()
        val rng = LinearCongruentGenerator(startKey)
        return source.map {
            if (!(A_CODE_Z_CODE_RANGE).contains(it.toInt())) {
                it
            } else {
                val newCode = (it.toInt() - A_CODE + rng.next()) %
                        ALPHABET_SIZE + A_CODE
                newCode.toChar()
            }
        }.joinToString("")
    }

    override fun decrypt(source: String, ctx: CipherContext): String {
        val startKey = ctx.startKey ?: throw NullCipherContextParamException()
        val rng = LinearCongruentGenerator(startKey)
        return source.map {
            if (!(A_CODE_Z_CODE_RANGE).contains(it.toInt())) {
                it
            } else {
                val key = rng.next()
                val alphabetPosition = it.toInt() - A_CODE
                val newCode = if (alphabetPosition + ALPHABET_SIZE >= key) {
                    (alphabetPosition + ALPHABET_SIZE - key) % ALPHABET_SIZE + A_CODE
                } else {
                    ALPHABET_SIZE - abs(alphabetPosition - key + ALPHABET_SIZE) %
                            ALPHABET_SIZE + A_CODE
                }
                newCode.toChar()
            }
        }.joinToString("")
    }
}
