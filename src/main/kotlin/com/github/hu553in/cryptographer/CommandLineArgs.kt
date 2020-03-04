package com.github.hu553in.cryptographer

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.SystemExitException
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
    val input by parser.storing(
        "--in",
        "--input",
        help = "input file path (text to encrypt/decrypt must consist " +
                "only of English letters, any whitespaces will be removed)"
    )
    val output by parser.storing(
        "--out",
        "--output",
        help = "output file path"
    )
    val shift by parser.storing(
            "--shift",
            help = "shift required for Caesar cipher"
        )
        { toInt() }
        .default<Int?>(null)
        .addValidator {
            if (cipher == Cryptographer.CAESAR && value == null) {
                throw SystemExitException("Invalid CLI args were passed. Run app with '--help' flag.", 1)
            }
        }
    val key by parser.storing(
            "--key",
            help = "key required for Vigenere cipher (must consist only of English " +
                    "letters, any whitespaces will be removed)"
        )
        {
            toUpperCase()
            replace(Regex("\\s"), "")
        }
        .default<String?>(null)
        .addValidator {
            if (
                cipher == Cryptographer.VIGENERE &&
                (value == null || value!!.isEmpty() || value!!.any {
                    !(Cryptographer.A_CODE..Cryptographer.Z_CODE).contains(it.toInt())
                })
            ) {
                throw SystemExitException("Invalid CLI args were passed. Run app with '--help' flag.", 1)
            }
        }
}
