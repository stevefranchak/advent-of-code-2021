package advent.of.code.days

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
            .windowed(3)
            .map { it.sum() }
            .windowed(2)
            .filter { it[0] < it[1] }
            .count()
    }
}
