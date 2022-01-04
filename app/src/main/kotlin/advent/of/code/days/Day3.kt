package advent.of.code.days

import kotlin.math.pow

@Suppress("unused")
class Day3 : IDay {
    override fun executeStar1(input: List<String>): Any {
        // Assumption: each bitstring has the same length
        val numBitsPerLine = input[0].length
        val bitCounter = MutableList(numBitsPerLine) { MutableList(2) { 0 } }
        input.forEach { bits ->
            bits.forEachIndexed { index: Int, bit: Char ->
                // Assumption: bit characters will only ever be '0' and '1'
                bitCounter[index][bit.digitToInt()]++
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
        val oxygenGeneratorRating = deriveRating(input, BitCriteria.MOST_COMMON)
        val co2ScrubberRating = deriveRating(input, BitCriteria.LEAST_COMMON)

        return oxygenGeneratorRating * co2ScrubberRating
    }

    enum class BitCriteria {
        MOST_COMMON,
        LEAST_COMMON,
    }

    private fun deriveRating(input: List<String>, bitCriteria: BitCriteria, currentIndex: Int = 0): UInt {
        if (input.size == 1) {
            return input[0].toUInt(radix = 2)
        }

        val (startsWithZero, startsWithOne) = input.asSequence()
            .map { Pair(it[currentIndex], it) }
            .partition { it.first == '0' }

        return deriveRating(
            when (bitCriteria) {
                BitCriteria.MOST_COMMON ->
                    if (startsWithZero.size > startsWithOne.size) startsWithZero.map() else startsWithOne.map()
                BitCriteria.LEAST_COMMON ->
                    if (startsWithOne.size < startsWithZero.size) startsWithOne.map() else startsWithZero.map()
            },
            bitCriteria,
            currentIndex + 1
        )
    }
}

fun List<Pair<Char, String>>.map(): List<String> = this.map { it.second }
