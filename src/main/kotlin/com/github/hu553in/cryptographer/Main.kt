package com.github.hu553in.cryptographer

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import org.slf4j.LoggerFactory
import java.io.File

fun invalidCliArgs() {
    error(INVALID_CLI_ARGS_ERROR_MSG)
}

fun main(args: Array<String>) = mainBody {
    val rootLogger = LoggerFactory.getLogger("root logger")
    ArgParser(args).parseInto(::CommandLineArgs).run {
        try {
            val source = File(input)
                .readText()
                .toUpperCase()
            val result = when (cipher) {
                CAESAR -> shift?.let {
                    when (action) {
                        ENCRYPT -> Cryptographer.encryptCaesar(source, it)
                        DECRYPT -> Cryptographer.decryptCaesar(source, it)
                        else -> invalidCliArgs()
                    }
                }
                VIGENERE -> key?.let {
                    when (action) {
                        ENCRYPT -> Cryptographer.encryptVigenere(source, it)
                        DECRYPT -> Cryptographer.decryptVigenere(source, it)
                        else -> invalidCliArgs()
                    }
                }
                AFFINE -> b?.let {
                    when (action) {
                        ENCRYPT -> Cryptographer.encryptAffine(source, it)
                        DECRYPT -> Cryptographer.decryptAffine(source, it)
                        else -> invalidCliArgs()
                    }
                }
                else -> invalidCliArgs()
            }
            File(output).writeText(result as String)
        } catch (e: Exception) {
            rootLogger.error("${e.javaClass.canonicalName} - ${e.message}")
        }
    }
}
