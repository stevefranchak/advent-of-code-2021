package advent.of.code

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.int
import io.github.cdimascio.dotenv.dotenv
import net.harawata.appdirs.AppDirsFactory
import kotlin.system.exitProcess

class App : CliktCommand() {
    companion object {
        const val APP_NAME = "advent_of_code_runner"
        const val ADVENT_OF_CODE_YEAR = "2021"
        const val ADVENT_OF_CODE_SESSION_TOKEN_ENV_NAME = "SESSION_TOKEN"
        const val CACHE_INPUT_FILES_LOCATION_ENV_NAME = "CACHE_INPUT_FILES_LOCATION"
        const val EXCEPTION_OCCURRED_EXIT_STATUS = 1
    }

    private val day: Int by argument(help = "Which day to run").int()
    private val star: Int by argument(help = "Which star to run within the given day").int()

    private val appDirs = AppDirsFactory.getInstance()
    private val dotenv = dotenv()

    override fun run() {
        val defaultCacheLocation = appDirs.getUserCacheDir(APP_NAME, ADVENT_OF_CODE_YEAR, null)
        val inputFetcher = InputFetcher(
            adventOfCodeSessionToken = dotenv.get(ADVENT_OF_CODE_SESSION_TOKEN_ENV_NAME),
            cacheLocation = dotenv.get(
                CACHE_INPUT_FILES_LOCATION_ENV_NAME, null
            ) ?: defaultCacheLocation
        )

        try {
            val input = inputFetcher.getInputForDay(day)
            val result = executeDayByStar(day, star, input)
            println(result)
        } catch (exc: Exception) {
            printException(exc)
            exitProcess(EXCEPTION_OCCURRED_EXIT_STATUS)
        }
    }

    private fun printException(exc: Exception) {
        print(
            buildString {
                appendLine(exc.message)
                var cause = exc.cause
                while (cause != null) {
                    appendLine("Caused by: ${cause.message}")
                    cause = cause.cause
                }
            }
        )
    }
}

fun main(args: Array<String>) {
    App().main(args)
}
