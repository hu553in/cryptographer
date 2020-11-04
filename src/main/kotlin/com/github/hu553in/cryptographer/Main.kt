package com.github.hu553in.cryptographer

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import org.slf4j.LoggerFactory
import java.io.File

fun main(args: Array<String>) = mainBody {
    val rootLogger = LoggerFactory.getLogger("root logger")
    ArgParser(args).parseInto(::CommandLineArgs).run {
        try {
            val source = File(input).readText().toUpperCase()
            rootLogger.info("SOURCE:\n\n$source")
            val result: String = when (action) {
                ENCRYPT -> {
                    when (cipher) {
                        CAESAR -> shift?.let { Cryptographer.encryptCaesar(source, it) }
                        VIGENERE -> key?.let { Cryptographer.encryptVigenere(source, it) }
                        AFFINE -> b?.let { Cryptographer.encryptAffine(source, it) }
                        GAMMA -> Cryptographer.encryptGamma(source, startKey)
                        else -> error(INVALID_CLI_ARGS_ERROR_MSG)
                    }
                }
                DECRYPT -> {
                    when (cipher) {
                        CAESAR -> shift?.let { Cryptographer.decryptCaesar(source, it) }
                        VIGENERE -> key?.let { Cryptographer.decryptVigenere(source, it) }
                        AFFINE -> b?.let { Cryptographer.decryptAffine(source, it) }
                        GAMMA -> Cryptographer.decryptGamma(source, startKey)
                        else -> error(INVALID_CLI_ARGS_ERROR_MSG)
                    }
                }
                BREAK -> {
                    when (cipher) {
                        VIGENERE -> Cryptographer.breakVigenere(source)
                        else -> error(INVALID_CLI_ARGS_ERROR_MSG)
                    }
                }
                else -> error(INVALID_CLI_ARGS_ERROR_MSG)
            } ?: error("Result is null")
            File(output).writeText(result)
            rootLogger.info("RESULT:\n\n$result")
        } catch (e: Exception) {
            rootLogger.error("${e.javaClass.canonicalName} - ${e.message}")
        }
    }
}
