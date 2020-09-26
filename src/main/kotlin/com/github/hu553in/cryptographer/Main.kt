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
            val result = when (action) {
                ENCRYPT -> {
                    when (cipher) {
                        CAESAR -> shift?.let { Cryptographer.encryptCaesar(source, it) }
                        VIGENERE -> key?.let { Cryptographer.encryptVigenere(source, it) }
                        AFFINE -> b?.let { Cryptographer.encryptAffine(source, it) }
                        else -> invalidCliArgs()
                    }
                }
                DECRYPT -> {
                    when (cipher) {
                        CAESAR -> shift?.let { Cryptographer.decryptCaesar(source, it) }
                        VIGENERE -> key?.let { Cryptographer.decryptVigenere(source, it) }
                        AFFINE -> b?.let { Cryptographer.decryptAffine(source, it) }
                        else -> invalidCliArgs()
                    }
                }
                CRYPTANALYSIS -> {
                    when (cipher) {
                        VIGENERE -> keyLength?.let { Cryptographer.cryptanalysisVigenere(source, it) }
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
