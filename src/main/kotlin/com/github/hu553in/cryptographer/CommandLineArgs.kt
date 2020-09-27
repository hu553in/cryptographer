package com.github.hu553in.cryptographer

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.SystemExitException
import com.xenomachina.argparser.default

class CommandLineArgs(parser: ArgParser) {
    val action by parser.mapping(
            "--$ENCRYPT" to ENCRYPT,
            "--$DECRYPT" to DECRYPT,
            "--$BREAK" to BREAK,
            help = "name of action to do"
    )
    val cipher by parser.mapping(
            "--$CAESAR" to CAESAR,
            "--$VIGENERE" to VIGENERE,
            "--$AFFINE" to AFFINE,
            help = "name of cipher to use"
    )
    val input by parser.storing(
            "--in",
            "--input",
            help = "input file path"
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
            help = "key required for Vigenere cipher encryption/decryption"
    )
    { toUpperCase() }
            .default<String?>(null)
            .addValidator {
                if (
                        cipher == VIGENERE &&
                        (action == ENCRYPT || action == DECRYPT) &&
                        (value == null || value!!.isEmpty())
                ) {
                    throw SystemExitException(INVALID_CLI_ARGS_ERROR_MSG, 1)
                }
            }
    val b by parser.storing(
            "--b",
            help = "b required for Affine cipher"
    )
    { toInt() }
            .default<Int?>(null)
            .addValidator {
                if (cipher == AFFINE && value == null) {
                    throw SystemExitException(INVALID_CLI_ARGS_ERROR_MSG, 1)
                }
            }
}
