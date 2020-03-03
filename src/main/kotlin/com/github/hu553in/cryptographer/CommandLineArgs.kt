package com.github.hu553in.cryptographer

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

class CommandLineArgs(parser: ArgParser) {
    val action by parser.mapping(
        "--encrypt" to Cryptographer.ENCRYPT,
        "--decrypt" to Cryptographer.DECRYPT,
        help = "name of action to do"
    )
    val cipher by parser.mapping(
        "--caesar" to Cryptographer.CAESAR,
        "--vigenere" to Cryptographer.VIGENERE,
        help = "name of cipher to use"
    )
    val source by parser.positional("SOURCE", help = "source string")
    val shift by parser.storing("--shift", help = "shift required for Caesar cipher") { toInt() }
        .default<Int?>(null)
        .addValidator {
            if (cipher == Cryptographer.CAESAR && value == null) {
                illegalArgumentException()
            }
        }
    val key by parser.storing("--key", help = "key required for Vigenere cipher")
        .default<String?>(null)
        .addValidator {
            if (cipher == Cryptographer.VIGENERE && (value == null || value!!.isEmpty())) {
                illegalArgumentException()
            }
        }
}
