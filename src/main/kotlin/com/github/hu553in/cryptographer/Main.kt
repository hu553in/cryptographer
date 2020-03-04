package com.github.hu553in.cryptographer

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import org.slf4j.LoggerFactory
import java.io.File

fun invalidCliArgs() {
    throw IllegalArgumentException("Invalid CLI args were passed. Run app with '--help' flag.");
}

fun main(args: Array<String>) = mainBody {
    val logger = LoggerFactory.getLogger("root logger")
    ArgParser(args).parseInto(::CommandLineArgs).run {
        try {
            val source = File(input)
                .readText()
                .replace(Regex("\\s"), "")
                .toUpperCase()
            if (
                source.isEmpty() ||
                source.any { !(Cryptographer.A_CODE..Cryptographer.Z_CODE).contains(it.toInt()) }
            ) {
                logger.info(source)
                invalidCliArgs()
            }
            val result = when (cipher) {
                Cryptographer.CAESAR -> shift?.let {
                    when (action) {
                        Cryptographer.ENCRYPT -> Cryptographer.encryptCaesar(source, it)
                        Cryptographer.DECRYPT -> Cryptographer.decryptCaesar(source, it)
                        else -> invalidCliArgs()
                    }
                }
                Cryptographer.VIGENERE -> key?.let {
                    when (action) {
                        Cryptographer.ENCRYPT -> Cryptographer.encryptVigenere(source, it)
                        Cryptographer.DECRYPT -> Cryptographer.decryptVigenere(source, it)
                        else -> invalidCliArgs()
                    }
                }
                else -> invalidCliArgs()
            }
            File(output).writeText(result as String)
            logger.info("SUCCESS")
        } catch (e: Exception) {
            logger.error("${e.javaClass.canonicalName} - ${e.message}")
        }
    }
}
