package com.github.hu553in.cryptographer.roles

import com.github.hu553in.cryptographer.utils.CipherContext

interface Breaker {
    fun `break`(source: String, ctx: CipherContext): String
}
