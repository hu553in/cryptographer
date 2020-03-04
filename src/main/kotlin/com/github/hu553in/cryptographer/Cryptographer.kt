package com.github.hu553in.cryptographer

object Cryptographer {
    const val CAESAR = "CAESAR";
    const val VIGENERE = "VIGENERE";
    const val ENCRYPT = "ENCRYPT";
    const val DECRYPT = "DECRYPT";
    const val A_CODE = 'A'.toInt()
    const val Z_CODE = 'Z'.toInt()

    private const val ALPHABET_SIZE = Z_CODE - A_CODE

    fun encryptCaesar(source: String, shift: Int): String {
        val realShift = shift % (ALPHABET_SIZE + 1)
        return source.map {
            var newCode = it.toInt() + realShift
            if (newCode > Z_CODE) {
                newCode -= ALPHABET_SIZE + 1
            } else if (newCode < A_CODE) {
                newCode += ALPHABET_SIZE + 1
            }
            newCode.toChar()
        }.joinToString("")
    }

    fun decryptCaesar(source: String, shift: Int) = encryptCaesar(source, -shift)

    fun encryptVigenere(source: String, key: String): String {
        var keyIterator = key.iterator()
        return source.map {
            if (!keyIterator.hasNext()) {
                keyIterator = key.iterator()
            }
            val shift = keyIterator.nextChar().toInt() - A_CODE
            var newCode = it.toInt() + shift
            if (newCode > Z_CODE) {
                newCode -= ALPHABET_SIZE + 1
            }
            newCode.toChar()
        }.joinToString("")
    }

    fun decryptVigenere(source: String, key: String): String {
        var keyIterator = key.iterator()
        return source.map {
            if (!keyIterator.hasNext()) {
                keyIterator = key.iterator()
            }
            val shift = keyIterator.nextChar().toInt() - A_CODE
            var newCode = it.toInt() - shift
            if (newCode < A_CODE) {
                newCode += ALPHABET_SIZE + 1
            }
            newCode.toChar()
        }.joinToString("")
    }
}
