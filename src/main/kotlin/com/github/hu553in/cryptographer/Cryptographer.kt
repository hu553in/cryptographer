package com.github.hu553in.cryptographer

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

    fun cryptanalysisVigenere(source: String, keyLength: Int) {
        val sourceParts = source.chunked(keyLength)
    }
}
