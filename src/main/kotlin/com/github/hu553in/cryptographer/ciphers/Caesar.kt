package com.github.hu553in.cryptographer.ciphers

import com.github.hu553in.cryptographer.exceptions.NullCipherContextParamException
import com.github.hu553in.cryptographer.roles.Decrypter
import com.github.hu553in.cryptographer.roles.Encryptor
import com.github.hu553in.cryptographer.utils.ALPHABET_SIZE
import com.github.hu553in.cryptographer.utils.A_CODE
import com.github.hu553in.cryptographer.utils.A_CODE_Z_CODE_RANGE
import com.github.hu553in.cryptographer.utils.CipherContext
import com.github.hu553in.cryptographer.utils.Z_CODE

object Caesar : Encryptor, Decrypter {
    override fun encrypt(source: String, ctx: CipherContext): String {
        val shift = ctx.shift ?: throw NullCipherContextParamException()
        val realShift = shift % (ALPHABET_SIZE)
        return source.uppercase().map {
            if (!(A_CODE_Z_CODE_RANGE).contains(it.code)) {
                it
            } else {
                var newCode = it.code + realShift
                if (newCode > Z_CODE) {
                    newCode -= ALPHABET_SIZE
                } else if (newCode < A_CODE) {
                    newCode += ALPHABET_SIZE
                }
                newCode.toChar()
            }
        }.joinToString("")
    }

    override fun decrypt(source: String, ctx: CipherContext): String {
        val shift = ctx.shift ?: throw NullCipherContextParamException()
        return encrypt(source, ctx.copy(shift = -shift))
    }
}
