package advent.of.code

import io.github.cdimascio.dotenv.dotenv

const val ADVENT_OF_CODE_SESSION_TOKEN_ENV_NAME = "SESSION_TOKEN"

private lateinit var inputFetcher: InputFetcher

fun main() {
    val dotenv = dotenv()
    inputFetcher = InputFetcher(dotenv.get(ADVENT_OF_CODE_SESSION_TOKEN_ENV_NAME))
    println(inputFetcher.getInputForDay(1))
}
