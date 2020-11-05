package com.github.hu553in.cryptographer.ciphers

import com.github.hu553in.cryptographer.exceptions.NullCipherContextParamException
import com.github.hu553in.cryptographer.roles.Decrypter
import com.github.hu553in.cryptographer.roles.Encryptor
import com.github.hu553in.cryptographer.utils.AFFINE_A
import com.github.hu553in.cryptographer.utils.AFFINE_A_SWAP
import com.github.hu553in.cryptographer.utils.ALPHABET_SIZE
import com.github.hu553in.cryptographer.utils.A_CODE
import com.github.hu553in.cryptographer.utils.A_CODE_Z_CODE_RANGE
import com.github.hu553in.cryptographer.utils.CipherContext

object Affine : Encryptor, Decrypter {
    override fun encrypt(source: String, ctx: CipherContext): String {
        val b = ctx.b ?: throw NullCipherContextParamException()
        return source.toUpperCase().map {
            if (!(A_CODE_Z_CODE_RANGE).contains(it.toInt())) {
                it
            } else {
                val newCode = (AFFINE_A * (it.toInt() - A_CODE) + b) %
                        ALPHABET_SIZE + A_CODE
                newCode.toChar()
            }
        }.joinToString("")
    }

    override fun decrypt(source: String, ctx: CipherContext): String {
        val b = ctx.b ?: throw NullCipherContextParamException()
        return source.toUpperCase().map {
            if (!(A_CODE_Z_CODE_RANGE).contains(it.toInt())) {
                it
            } else {
                val newCode = AFFINE_A_SWAP * ((it.toInt() - A_CODE) - b) %
                        ALPHABET_SIZE + A_CODE
                newCode.toChar()
            }
        }.joinToString("")
    }
}
