package advent.of.code.days

@Suppress("unused")
class Day2 : IDay {
    override fun executeStar1(input: List<String>): Any {
        var depth = 0
        var horizontalPosition = 0

        input.asSequence().map {
            val (command, value) = it.split(' ')
            Pair(command, value.toInt())
        }.forEach {
            when (it.first) {
                "forward" -> horizontalPosition += it.second
                "down" -> depth += it.second
                "up" -> depth -= it.second
            }
        }

        return depth * horizontalPosition
    }

    override fun executeStar2(input: List<String>): Any {
        var depth = 0
        var horizontalPosition = 0
        var aim = 0

        input.asSequence().map {
            val (command, value) = it.split(' ')
            Pair(command, value.toInt())
        }.forEach {
            when (it.first) {
                "forward" -> {
                    horizontalPosition += it.second
                    depth += aim * it.second
                }
                "down" -> aim += it.second
                "up" -> aim -= it.second
            }
        }

        return depth * horizontalPosition
    }
}
