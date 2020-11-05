package com.github.hu553in.cryptographer.roles

import com.github.hu553in.cryptographer.utils.CipherContext

interface Decrypter {
    fun decrypt(source: String, ctx: CipherContext): String
}
