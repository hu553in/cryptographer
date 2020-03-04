package com.github.hu553in.cryptographer

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.SystemExitException
import com.xenomachina.argparser.default

class CommandLineArgs(parser: ArgParser) {
    val action by parser.mapping(
        "--$ENCRYPT" to ENCRYPT,
        "--$DECRYPT" to DECRYPT,
        help = "name of action to do"
    )
    val cipher by parser.mapping(
        "--$CAESAR" to CAESAR,
        "--$VIGENERE" to VIGENERE,
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
            if (cipher == CAESAR && value == null) {
                throw SystemExitException(INVALID_CLI_ARGS_ERROR_MSG, 1)
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
                cipher == VIGENERE &&
                (value == null || value!!.isEmpty() || value!!.any {
                    !(A_CODE_Z_CODE_RANGE).contains(it.toInt())
                })
            ) {
                throw SystemExitException(INVALID_CLI_ARGS_ERROR_MSG, 1)
            }
        }
}
