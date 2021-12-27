package advent.of.code.days

import kotlin.math.pow

class Day3 : IDay {
    override fun executeStar1(input: List<String>): Any {
        // Assumption: each bitstring has the same length
        val numBitsPerLine = input[0].length
        val bitCounter = MutableList(numBitsPerLine) { MutableList(2) { 0 } }
        input.forEach { bits ->
            bits.forEachIndexed { index: Int, c: Char ->
                bitCounter[index][c.digitToInt()]++
            }
        }

        val gammaRateInBits = bitCounter.joinToString(
            separator = "",
            transform = {
                // Assumption: count of 0's can never equal count of 1's
                if (it[0] > it[1]) "0" else "1"
            }
        )

        val gammaRate = gammaRateInBits.toUInt(radix = 2)
        // Invert bits and force bits in (positions > numBitsPerLine from the least significant bit) to 0
        val epsilonRate = gammaRate.inv() and (2f.pow(numBitsPerLine).toUInt() - 1u)
        return gammaRate * epsilonRate
    }

    override fun executeStar2(input: List<String>): Any {
        return input
    }
}
