package advent.of.code

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

class InputFetcher(private val adventOfCodeSessionToken: String?) {

    // TODO: cache in filesystem; if not cached, fetch from AoC server
    fun getInputForDay(day: Int): String? {
        val (_, _, result) = "https://adventofcode.com/2021/day/$day/input"
            .httpGet()
            .header(Pair("Cookie", "session=$adventOfCodeSessionToken"))
            .responseString()
        when (result) {
            is Result.Failure -> {
                // TODO: eventually make this better
                val ex = result.getException()
                println(ex.message)
            }
            is Result.Success -> {
                return result.get()
            }
        }

        return null
    }
}