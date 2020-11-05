package com.github.hu553in.cryptographer.utils

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
        "--$GAMMA" to GAMMA,
        "--$FEISTEL_NETWORK" to FEISTEL_NETWORK,
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
    {
        try {
            toInt()
        } catch (e: Exception) {
            throw SystemExitException(INVALID_CLI_ARGS_ERROR_MSG, 1)
        }
    }
        .default<Int?>(null)
        .addValidator {
            if (cipher == CAESAR && value == null) {
                throw SystemExitException(INVALID_CLI_ARGS_ERROR_MSG, 1)
            }
        }
    val key by parser.storing(
        "--key",
        help = "key required for Vigenere cipher encryption/decryption " +
                "(must consist of English letters only)"
    )
    {
        try {
            toUpperCase()
        } catch (e: Exception) {
            throw SystemExitException(INVALID_CLI_ARGS_ERROR_MSG, 1)
        }
    }
        .default<String?>(null)
        .addValidator {
            if (
                cipher == VIGENERE &&
                (action == ENCRYPT || action == DECRYPT) &&
                (value == null || value!!.isEmpty() || value!!.any {
                    !A_CODE_Z_CODE_RANGE.contains(it.toInt())
                })
            ) {
                throw SystemExitException(INVALID_CLI_ARGS_ERROR_MSG, 1)
            }
        }
    val startKey by parser.storing(
        "--startKey",
        help = "start key for gamma cipher encryption/decryption (defaults to 1)"
    )
    {
        try {
            toInt()
        } catch (e: Exception) {
            throw SystemExitException(INVALID_CLI_ARGS_ERROR_MSG, 1)
        }
    }
        .default<Int>(1)
    val b by parser.storing(
        "--b",
        help = "b required for Affine cipher"
    )
    {
        try {
            toInt()
        } catch (e: Exception) {
            throw SystemExitException(INVALID_CLI_ARGS_ERROR_MSG, 1)
        }
    }
        .default<Int?>(null)
        .addValidator {
            if (cipher == AFFINE && value == null) {
                throw SystemExitException(INVALID_CLI_ARGS_ERROR_MSG, 1)
            }
        }
}
