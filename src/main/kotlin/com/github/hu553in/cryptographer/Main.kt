package com.github.hu553in.cryptographer

import com.xenomachina.argparser.ArgParser
import org.slf4j.LoggerFactory

fun illegalArgumentException(msg: String? = "Invalid CLI args were passed. Run app with '--help' flag.") {
    throw IllegalArgumentException(msg);
}

fun main(args: Array<String>) {
    val logger = LoggerFactory.getLogger("root")
    try {
        ArgParser(args).parseInto(::CommandLineArgs).run {
            val result = when (cipher) {
                Cryptographer.CAESAR -> shift?.let {
                    when (action) {
                        Cryptographer.ENCRYPT -> Cryptographer.encryptCaesar(source, it)
                        Cryptographer.DECRYPT -> Cryptographer.decryptCaesar(source, it)
                        else -> illegalArgumentException()
                    }
                }
                Cryptographer.VIGENERE -> key?.let {
                    when (action) {
                        Cryptographer.ENCRYPT -> Cryptographer.encryptVigenere(source, it)
                        Cryptographer.DECRYPT -> Cryptographer.decryptVigenere(source, it)
                        else -> illegalArgumentException()
                    }
                }
                else -> illegalArgumentException()
            }
            println(result)
        }
    } catch (e: Exception) {
        logger.error(e.message)
    }
}
