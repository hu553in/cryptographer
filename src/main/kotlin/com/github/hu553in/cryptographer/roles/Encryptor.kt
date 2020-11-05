package com.github.hu553in.cryptographer.roles

import com.github.hu553in.cryptographer.utils.CipherContext

interface Encryptor {
    fun encrypt(source: String, ctx: CipherContext): String
}
