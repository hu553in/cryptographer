package com.github.hu553in.cryptographer

const val CAESAR = "caesar"
const val VIGENERE = "vigenere"
const val AFFINE = "affine"

const val ENCRYPT = "encrypt"
const val DECRYPT = "decrypt"
const val BREAK = "break"

const val ENGLISH_INDEX_OF_COINCIDENCE = 0.0667
const val MAX_ESTIMATED_KEY_LENGTH = 25

const val A_CODE = 'A'.toInt()
const val Z_CODE = 'Z'.toInt()

const val ALPHABET_SIZE = Z_CODE - A_CODE + 1
val A_CODE_Z_CODE_RANGE = A_CODE..Z_CODE

val ENGLISH_ALPHABET_STANDARD_FREQUENCIES = mapOf(
        'E' to 0.12702, 'T' to 0.09056, 'A' to 0.08167, 'O' to 0.07507, 'I' to 0.06966, 'N' to 0.06749, 'S' to 0.06327,
        'H' to 0.06094, 'R' to 0.05987, 'D' to 0.04253, 'L' to 0.04025, 'C' to 0.02782, 'U' to 0.02758, 'M' to 0.02406,
        'W' to 0.02360, 'F' to 0.02228, 'G' to 0.02015, 'Y' to 0.01974, 'P' to 0.01929, 'B' to 0.01492, 'V' to 0.00978,
        'K' to 0.00772, 'J' to 0.00153, 'X' to 0.00150, 'Q' to 0.00095, 'Z' to 0.00074
)

const val AFFINE_A = 3
const val AFFINE_A_SWAP = 9

const val INVALID_CLI_ARGS_ERROR_MSG = "Invalid CLI args were passed. Run app with '--help' flag."
