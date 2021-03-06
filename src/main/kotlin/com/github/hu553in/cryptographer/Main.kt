package com.github.hu553in.cryptographer

import com.github.hu553in.cryptographer.ciphers.Affine
import com.github.hu553in.cryptographer.ciphers.Caesar
import com.github.hu553in.cryptographer.ciphers.FeistelNetwork
import com.github.hu553in.cryptographer.ciphers.Gamma
import com.github.hu553in.cryptographer.ciphers.Vigenere
import com.github.hu553in.cryptographer.exceptions.NullCipherContextParamException
import com.github.hu553in.cryptographer.roles.Breaker
import com.github.hu553in.cryptographer.roles.Decrypter
import com.github.hu553in.cryptographer.roles.Encryptor
import com.github.hu553in.cryptographer.utils.AFFINE
import com.github.hu553in.cryptographer.utils.BREAK
import com.github.hu553in.cryptographer.utils.CAESAR
import com.github.hu553in.cryptographer.utils.CipherContext
import com.github.hu553in.cryptographer.utils.CommandLineArgs
import com.github.hu553in.cryptographer.utils.DECRYPT
import com.github.hu553in.cryptographer.utils.ENCRYPT
import com.github.hu553in.cryptographer.utils.FEISTEL_NETWORK
import com.github.hu553in.cryptographer.utils.GAMMA
import com.github.hu553in.cryptographer.utils.INVALID_CLI_ARGS_ERROR_MSG
import com.github.hu553in.cryptographer.utils.VIGENERE
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import java.io.File
import org.slf4j.LoggerFactory

fun main(args: Array<String>) = mainBody {
    val logger = LoggerFactory.getLogger("root logger")
    ArgParser(args).parseInto(::CommandLineArgs).run {
        try {
            val ctx = CipherContext(startKey, b, shift, key)
            val source = File(input).readText()
            val result: String = try {
                when (action) {
                    ENCRYPT -> {
                        mapOf<String, Encryptor>(
                            CAESAR to Caesar,
                            VIGENERE to Vigenere,
                            AFFINE to Affine,
                            GAMMA to Gamma,
                            FEISTEL_NETWORK to FeistelNetwork
                        )[cipher]
                            ?.encrypt(source, ctx)
                            ?: error(INVALID_CLI_ARGS_ERROR_MSG)
                    }
                    DECRYPT -> {
                        mapOf<String, Decrypter>(
                            CAESAR to Caesar,
                            VIGENERE to Vigenere,
                            AFFINE to Affine,
                            GAMMA to Gamma,
                            FEISTEL_NETWORK to FeistelNetwork
                        )[this.cipher]
                            ?.decrypt(source, ctx)
                            ?: error(INVALID_CLI_ARGS_ERROR_MSG)
                    }
                    BREAK -> {
                        mapOf<String, Breaker>(VIGENERE to Vigenere)[this.cipher]
                            ?.`break`(source, ctx)
                            ?: error(INVALID_CLI_ARGS_ERROR_MSG)
                    }
                    else -> error(INVALID_CLI_ARGS_ERROR_MSG)
                }
            } catch (e: NullCipherContextParamException) {
                error(INVALID_CLI_ARGS_ERROR_MSG)
            }
            File(output).writeText(result)
            val printableSource = source.replace(Regex("(\\p{C})"), "?");
            val printableResult = result.replace(Regex("(\\p{C})"), "?");
            logger.info(
                "\n\n===== SOURCE =====\n\n$printableSource\n\n" +
                    "===== RESULT =====\n\n$printableResult"
            )
        } catch (e: Exception) {
            logger.error("${e.javaClass.canonicalName} - ${e.message}")
        }
    }
}
