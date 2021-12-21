package advent.of.code

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import java.io.File
import kotlin.io.path.Path

// I wish I were clever enough to come up with the structure of this - see: https://stackoverflow.com/a/68775013
class InputFetchException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}

class InputFetcher(
    private val adventOfCodeSessionToken: String?,
    private val cacheLocation: String
) {

    fun getInputForDay(day: Int): List<String> {
        val cacheFileLocation = File(cacheLocation)
        cacheFileLocation.mkdirs()
        val cacheFile = File(cacheFileLocation, "input-$day.txt")

        if (!cacheFile.exists()) {
            val inputString = fetchInputForDayFromSite(day)
            cacheFile.writeText(inputString)
            return inputString.split('\n')
        }

        return cacheFile.readLines()
    }

    private fun fetchInputForDayFromSite(day: Int): String {
        val (_, _, result) = "https://adventofcode.com/2021/day/$day/input"
                .httpGet()
                .header(Pair("Cookie", "session=$adventOfCodeSessionToken"))
                .responseString()
        when (result) {
            is Result.Failure -> {
                throw InputFetchException(
                    "Failed to get input from Advent of Code's site for Day $day", result.getException()
                )
            }
            is Result.Success -> {
                return result.get().trim()
            }
        }
    }
}