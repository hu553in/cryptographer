package com.github.hu553in.cryptographer

import kotlin.math.abs

object Cryptographer {
    fun encryptCaesar(source: String, shift: Int): String {
        val realShift = shift % (ALPHABET_SIZE)
        return source.map {
            if (!(A_CODE_Z_CODE_RANGE).contains(it.toInt())) {
                it
            } else {
                var newCode = it.toInt() + realShift
                if (newCode > Z_CODE) {
                    newCode -= ALPHABET_SIZE
                } else if (newCode < A_CODE) {
                    newCode += ALPHABET_SIZE
                }
                newCode.toChar()
            }
        }.joinToString("")
    }

    fun decryptCaesar(source: String, shift: Int) = encryptCaesar(source, -shift)

    fun encryptVigenere(source: String, key: String): String {
        var keyIterator = key.iterator()
        return source.map {
            if (!(A_CODE_Z_CODE_RANGE).contains(it.toInt())) {
                it
            } else {
                if (!keyIterator.hasNext()) {
                    keyIterator = key.iterator()
                }
                val shift = keyIterator.nextChar().toInt() - A_CODE
                var newCode = it.toInt() + shift
                if (newCode > Z_CODE) {
                    newCode -= ALPHABET_SIZE
                }
                newCode.toChar()
            }
        }.joinToString("")
    }

    fun decryptVigenere(source: String, key: String): String {
        var keyIterator = key.iterator()
        return source.map {
            if (!(A_CODE_Z_CODE_RANGE).contains(it.toInt())) {
                it
            } else {
                if (!keyIterator.hasNext()) {
                    keyIterator = key.iterator()
                }
                val shift = keyIterator.nextChar().toInt() - A_CODE
                var newCode = it.toInt() - shift
                if (newCode < A_CODE) {
                    newCode += ALPHABET_SIZE
                }
                newCode.toChar()
            }
        }.joinToString("")
    }

    fun encryptAffine(source: String, b: Int): String {
        return source.map {
            if (!(A_CODE_Z_CODE_RANGE).contains(it.toInt())) {
                it
            } else {
                val newCode = (AFFINE_A * (it.toInt() - A_CODE) + b) % ALPHABET_SIZE + A_CODE
                newCode.toChar()
            }
        }.joinToString("")
    }

    fun decryptAffine(source: String, b: Int): String {
        return source.map {
            if (!(A_CODE_Z_CODE_RANGE).contains(it.toInt())) {
                it
            } else {
                val newCode = AFFINE_A_SWAP * ((it.toInt() - A_CODE) - b) % ALPHABET_SIZE + A_CODE
                newCode.toChar()
            }
        }.joinToString("")
    }

    private fun countIndexOfCoincidenceVigenere(source: String, estimatedLength: Int): Double {
        val groupsOfNthElements = splitStringToGroupsOfNthElements(source, estimatedLength)
        val indicesOfCoincidenceForGroups = groupsOfNthElements.map { group ->
            val frequencies = A_CODE_Z_CODE_RANGE.map { letterCode ->
                group.count { it == letterCode.toChar() }.toDouble()
            }
            A_CODE_Z_CODE_RANGE.fold(0.0) { indexOfCoincidenceForGroup, letterCode ->
                indexOfCoincidenceForGroup +
                        (frequencies[letterCode - A_CODE] * (frequencies[letterCode - A_CODE] - 1)) /
                        (group.length * (group.length - 1))
            }
        }
        return indicesOfCoincidenceForGroups.average()
    }

    private fun findKeyLengthVigenere(source: String): Int {
        var keyLength = 0
        var difference = Double.MAX_VALUE
        for (newKeyLength in 2..MAX_ESTIMATED_KEY_LENGTH) {
            val newIndexOfCoincidence = countIndexOfCoincidenceVigenere(source, newKeyLength)
            if (newIndexOfCoincidence.isNaN()) break
            val newDifference = abs(ENGLISH_INDEX_OF_COINCIDENCE - newIndexOfCoincidence)
            if (newDifference < difference) {
                difference = newDifference
                keyLength = newKeyLength
            }
        }
        return keyLength
    }

    private fun splitStringToGroupsOfNthElements(source: String, groupCount: Int): List<String> {
        val sourceChunks = source.chunked(groupCount)
        return MutableList(groupCount) { mutableListOf<Char>() }.apply {
            sourceChunks.forEach { chunk -> chunk.forEachIndexed { index, char -> this[index].add(char) } }
        }.map { it.joinToString("") }
    }

    private fun decryptGroupOfNthElementsVigenere(group: String): String {
        var correlation = 0.0
        var decryptedGroup = ""
        (0 until ALPHABET_SIZE).forEach { alphabetIndex ->
            val newDecryptedGroupOfNthElements = decryptCaesar(group, alphabetIndex)
            val frequencies = A_CODE_Z_CODE_RANGE.map { letterCode ->
                newDecryptedGroupOfNthElements.count { it == letterCode.toChar() }.toDouble()
            }
            val newCorrelation = A_CODE_Z_CODE_RANGE.fold(0.0) { reducer, element ->
                val standardFrequency = ENGLISH_ALPHABET_STANDARD_FREQUENCIES.getValue(element.toChar())
                reducer + standardFrequency * frequencies[element - A_CODE]
            }
            if (newCorrelation > correlation) {
                correlation = newCorrelation
                decryptedGroup = newDecryptedGroupOfNthElements
            }
        }
        return decryptedGroup
    }

    fun breakVigenere(rawSource: String): String {
        val nonEnglishAlphabetSymbols = Regex("[^A-Z]")
                .findAll(rawSource)
                .associate { it.range.first to it.value }
                .toSortedMap()
        val source = rawSource.replace(Regex("[^A-Z]"), String())
        val keyLength = findKeyLengthVigenere(source)
        val groupsOfNthElements = splitStringToGroupsOfNthElements(source, keyLength)
        val decryptedGroupsOfNthElements = groupsOfNthElements.map { groupOfNthElements ->
            decryptGroupOfNthElementsVigenere(groupOfNthElements)
        }
        return StringBuilder().apply {
            for (biggestDecryptedGroupElementIndex in decryptedGroupsOfNthElements[0].indices) {
                for (decryptedGroupIndex in decryptedGroupsOfNthElements.indices) {
                    if (biggestDecryptedGroupElementIndex < decryptedGroupsOfNthElements[decryptedGroupIndex].length) {
                        append(decryptedGroupsOfNthElements[decryptedGroupIndex][biggestDecryptedGroupElementIndex])
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
