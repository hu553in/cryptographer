package com.github.hu553in.cryptographer.ciphers

import com.github.hu553in.cryptographer.exceptions.NullCipherContextParamException
import com.github.hu553in.cryptographer.roles.Breaker
import com.github.hu553in.cryptographer.roles.Decrypter
import com.github.hu553in.cryptographer.roles.Encryptor
import com.github.hu553in.cryptographer.utils.ALPHABET_SIZE
import com.github.hu553in.cryptographer.utils.ALPHABET_STANDARD_FREQUENCIES
import com.github.hu553in.cryptographer.utils.A_CODE
import com.github.hu553in.cryptographer.utils.A_CODE_Z_CODE_RANGE
import com.github.hu553in.cryptographer.utils.CipherContext
import com.github.hu553in.cryptographer.utils.MAX_ESTIMATED_KEY_LENGTH
import com.github.hu553in.cryptographer.utils.STANDARD_INDEX_OF_COINCIDENCE
import com.github.hu553in.cryptographer.utils.Z_CODE
import kotlin.math.abs

object Vigenere : Encryptor, Decrypter, Breaker {
    override fun encrypt(source: String, ctx: CipherContext): String {
        val key = ctx.key ?: throw NullCipherContextParamException()
        var keyIterator = key.iterator()
        return source.uppercase().map {
            if (!(A_CODE_Z_CODE_RANGE).contains(it.code)) {
                it
            } else {
                if (!keyIterator.hasNext()) {
                    keyIterator = key.iterator()
                }
                val shift = keyIterator.nextChar().code - A_CODE
                var newCode = it.code + shift
                if (newCode > Z_CODE) {
                    newCode -= ALPHABET_SIZE
                }
                newCode.toChar()
            }
        }.joinToString("")
    }

    override fun decrypt(source: String, ctx: CipherContext): String {
        val key = ctx.key ?: throw NullCipherContextParamException()
        var keyIterator = key.iterator()
        return source.uppercase().map {
            if (!(A_CODE_Z_CODE_RANGE).contains(it.code)) {
                it
            } else {
                if (!keyIterator.hasNext()) {
                    keyIterator = key.iterator()
                }
                val shift = keyIterator.nextChar().code - A_CODE
                var newCode = it.code - shift
                if (newCode < A_CODE) {
                    newCode += ALPHABET_SIZE
                }
                newCode.toChar()
            }
        }.joinToString("")
    }

    private fun countIndexOfCoincidence(source: String, estimatedLength: Int) =
        splitStringToGroupsOfNthElements(source, estimatedLength).map { group ->
            val frequencies = A_CODE_Z_CODE_RANGE.map { letterCode ->
                group.count { it == letterCode.toChar() }.toDouble()
            }
            A_CODE_Z_CODE_RANGE.fold(0.0) { indexOfCoincidenceForGroup, letterCode ->
                val currentFrequency = frequencies[letterCode - A_CODE]
                indexOfCoincidenceForGroup +
                        (currentFrequency * (currentFrequency - 1)) /
                        (group.length * (group.length - 1))
            }
        }.average()

    private fun findKeyLength(source: String): Int {
        var keyLength = 0
        var difference = Double.MAX_VALUE
        for (newKeyLength in 2..MAX_ESTIMATED_KEY_LENGTH) {
            val newIndexOfCoincidence = countIndexOfCoincidence(source, newKeyLength)
            if (newIndexOfCoincidence.isNaN()) break
            val newDifference = abs(STANDARD_INDEX_OF_COINCIDENCE - newIndexOfCoincidence)
            if (newDifference < difference) {
                difference = newDifference
                keyLength = newKeyLength
            }
        }
        return keyLength
    }

    private fun splitStringToGroupsOfNthElements(
        source: String,
        groupCount: Int
    ) = MutableList(groupCount) { mutableListOf<Char>() }.apply {
        source.chunked(groupCount).forEach { chunk ->
            chunk.forEachIndexed { index, char ->
                this[index].add(char)
            }
        }
    }.map { it.joinToString("") }

    private fun decryptGroupOfNthElements(group: String): String {
        var correlation = 0.0
        var decryptedGroup = ""
        (0 until ALPHABET_SIZE).forEach { alphabetIndex ->
            val newDecryptedGroupOfNthElements = Caesar.decrypt(
                group,
                CipherContext(shift = alphabetIndex)
            )
            val frequencies = A_CODE_Z_CODE_RANGE.map { letterCode ->
                newDecryptedGroupOfNthElements.count {
                    it == letterCode.toChar()
                }.toDouble()
            }
            val newCorrelation = A_CODE_Z_CODE_RANGE.fold(0.0) { reducer, element ->
                val standardFrequency = ALPHABET_STANDARD_FREQUENCIES.getValue(
                    element.toChar()
                )
                reducer + standardFrequency * frequencies[element - A_CODE]
            }
            if (newCorrelation > correlation) {
                correlation = newCorrelation
                decryptedGroup = newDecryptedGroupOfNthElements
            }
        }
        return decryptedGroup
    }

    override fun `break`(
        source: String,
        @Suppress("UNUSED_PARAMETER")
        ctx: CipherContext
    ): String {
        val upperCaseSource = source.uppercase()
        val nonEnglishAlphabetSymbols = Regex("[^A-Z]")
            .findAll(upperCaseSource)
            .associate { it.range.first to it.value }
            .toSortedMap()
        val cleanedSource = upperCaseSource.replace(Regex("[^A-Z]"), String())
        val keyLength = findKeyLength(cleanedSource)
        val groupsOfNthElements = splitStringToGroupsOfNthElements(cleanedSource, keyLength)
        val decryptedGroupsOfNthElements = groupsOfNthElements.map { groupOfNthElements ->
            decryptGroupOfNthElements(groupOfNthElements)
        }
        return StringBuilder().apply {
            for (biggestDecryptedGroupElementIndex in decryptedGroupsOfNthElements[0].indices) {
                for (decryptedGroupIndex in decryptedGroupsOfNthElements.indices) {
                    if (
                        biggestDecryptedGroupElementIndex <
                        decryptedGroupsOfNthElements[decryptedGroupIndex].length
                    ) {
                        append(
                            decryptedGroupsOfNthElements
                                    [decryptedGroupIndex]
                                    [biggestDecryptedGroupElementIndex]
                        )
                    }
                }
            }
            nonEnglishAlphabetSymbols.forEach {
                if (it.key > length) {
                    append(it.value)
                } else {
                    insert(it.key, it.value)
                }
            }
        }.toString()
    }
}
