package com.github.hu553in.cryptographer

object Cryptographer {
    const val CAESAR = "CAESAR";
    const val VIGENERE = "VIGENERE";
    const val ENCRYPT = "ENCRYPT";
    const val DECRYPT = "DECRYPT";

    private const val A_CODE = 'a'.toInt()
    private const val Z_CODE = 'z'.toInt()
    private const val ALPHABET_SIZE = Z_CODE - A_CODE

    private fun validateString(string: String) {
        if (string.any { !(A_CODE..Z_CODE).contains(it.toInt()) }) {
            illegalArgumentException("String must consist only of lowercase English letters")
        }
    }

    fun encryptCaesar(source: String, shift: Int): String {
        validateString(source)
        return source.map {
            var newCode = it.toInt() + shift
            if (newCode > Z_CODE) {
                newCode -= (ALPHABET_SIZE + 1)
            }
            newCode.toChar()
        }.joinToString("")
    }

    fun decryptCaesar(source: String, shift: Int): String {
        validateString(source)
        return source.map {
            var newCode = it.toInt() - shift
            if (newCode < A_CODE) {
                newCode += (ALPHABET_SIZE + 1)
            }
            newCode.toChar()
        }.joinToString("")
    }

    fun encryptVigenere(source: String, key: String): String {
        validateString(source)
        validateString(key)
        var keyIterator = key.iterator()
        return source.map {
            if (!keyIterator.hasNext()) {
                keyIterator = key.iterator()
            }
            val shift = keyIterator.nextChar().toInt() - A_CODE
            var newCode = it.toInt() + shift
            if (newCode > Z_CODE) {
                newCode -= (ALPHABET_SIZE + 1)
            }
            newCode.toChar()
        }.joinToString("")
    }

    fun decryptVigenere(source: String, key: String): String {
        validateString(source)
        validateString(key)
        var keyIterator = key.iterator()
        return source.map {
            if (!keyIterator.hasNext()) {
                keyIterator = key.iterator()
            }
            val shift = keyIterator.nextChar().toInt() - A_CODE
            var newCode = it.toInt() - shift
            if (newCode < A_CODE) {
                newCode += (ALPHABET_SIZE + 1)
            }
            newCode.toChar()
        }.joinToString("")
    }
}
