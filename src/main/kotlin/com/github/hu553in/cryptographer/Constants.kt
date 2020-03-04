package com.github.hu553in.cryptographer

const val CAESAR = "caesar"
const val VIGENERE = "vigenere"

const val ENCRYPT = "encrypt"
const val DECRYPT = "decrypt"

const val A_CODE = 'A'.toInt()
const val Z_CODE = 'Z'.toInt()

const val ALPHABET_SIZE = Z_CODE - A_CODE
val A_CODE_Z_CODE_RANGE = A_CODE..Z_CODE

const val INVALID_CLI_ARGS_ERROR_MSG = "Invalid CLI args were passed. Run app with '--help' flag."
