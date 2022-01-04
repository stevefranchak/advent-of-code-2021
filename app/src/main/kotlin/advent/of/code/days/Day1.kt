package advent.of.code.days

@Suppress("unused")
class Day1 : IDay {
    override fun executeStar1(input: List<String>): Any {
        return input.asSequence()
            .map(String::toInt)
            .windowed(2)
            .filter { it[0] < it[1] }
            .count()
    }

    override fun executeStar2(input: List<String>): Any {
        return input.asSequence()
            .map(String::toInt)
            // sum a sliding window of three values
            .windowed(3)
            .map { it.sum() }
            // only count increases between two subsequent sums
            .windowed(2)
            .filter { it[0] < it[1] }
            .count()
    }
}
