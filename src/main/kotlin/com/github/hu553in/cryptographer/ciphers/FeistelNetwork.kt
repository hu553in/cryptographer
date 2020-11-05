package com.github.hu553in.cryptographer.ciphers

import com.github.hu553in.cryptographer.roles.Decrypter
import com.github.hu553in.cryptographer.roles.Encryptor
import com.github.hu553in.cryptographer.utils.CipherContext
import com.github.hu553in.cryptographer.utils.FEISTEL_NETWORK_BLOCK_LENGTH
import com.github.hu553in.cryptographer.utils.FEISTEL_NETWORK_KEYS
import kotlin.experimental.xor

object FeistelNetwork : Encryptor, Decrypter {
    @ExperimentalStdlibApi
    override fun encrypt(
        source: String,
        @Suppress("UNUSED_PARAMETER")
        ctx: CipherContext
    ) = splitStringToBlocks(source).joinToString("") { encryptBlock(it) }

    @ExperimentalStdlibApi
    override fun decrypt(
        source: String,
        @Suppress("UNUSED_PARAMETER")
        ctx: CipherContext
    ) = splitStringToBlocks(source).joinToString("") { decryptBlock(it) }

    @ExperimentalStdlibApi
    private fun encryptBlock(block: String): String {
        var (left, right) = splitStringOnTwoHalves(block)
        FEISTEL_NETWORK_KEYS.forEach {
            val newLeft = xorEachPairOfElementsOfTwoStrings(
                left,
                xorStringElementsWithNumber(right, calculateRoundKey(it))
            )
            left = right
            right = newLeft
        }
        return left + right
    }

    @ExperimentalStdlibApi
    private fun decryptBlock(block: String): String {
        var (left, right) = splitStringOnTwoHalves(block)
        FEISTEL_NETWORK_KEYS.reversed().forEach {
            val newLeft = xorEachPairOfElementsOfTwoStrings(
                right,
                xorStringElementsWithNumber(left, calculateRoundKey(it))
            )
            right = left
            left = newLeft
        }
        return left + right
    }

    private fun splitStringOnTwoHalves(string: String) =
        string.substring(0, string.length / 2) to
                string.substring(string.length / 2)

    @ExperimentalStdlibApi
    private fun xorEachPairOfElementsOfTwoStrings(
        first: String,
        second: String
    ) = second.toByteArray().let {
        first.toByteArray()
            .mapIndexed { index, elem -> elem.xor(it[index]) }
            .toByteArray()
            .decodeToString()
    }

    @ExperimentalStdlibApi
    private fun xorStringElementsWithNumber(
        string: String,
        number: Int
    ) = string.toByteArray().apply {
        forEachIndexed { index, elem ->
            set(index, elem.toInt().xor(number).toByte())
        }
    }.decodeToString()

    private fun calculateRoundKey(key: Int) = key * 7 / 2

    private fun splitStringToBlocks(string: String) =
        (string.indices step FEISTEL_NETWORK_BLOCK_LENGTH).map {
            if (it + FEISTEL_NETWORK_BLOCK_LENGTH < string.length) {
                string.substring(it, it + FEISTEL_NETWORK_BLOCK_LENGTH)
            } else {
                StringBuilder(string.substring(it))
                    .apply {
                        repeat(FEISTEL_NETWORK_BLOCK_LENGTH - this.length) {
                            append(" ")
                        }
                    }
                    .toString()
            }
        }
}
