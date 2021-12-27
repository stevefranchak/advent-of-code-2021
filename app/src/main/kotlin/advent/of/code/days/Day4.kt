package advent.of.code.days

private val SUBSEQUENT_WHITESPACE_REGEX = """\s+""".toRegex()

class Day4 : IDay {

    override fun executeStar1(input: List<String>): Any {
        val boards = BingoBoards()
        val inputIterator = input.iterator()
        val numbersToDraw = inputIterator.next().split(',').map { it.toUInt() }
        inputIterator.next() // empty line

        while (inputIterator.hasNext()) {
            val board = BingoBoard(List(5) { inputIterator.next() })
            boards.addBoard(board)
            if (inputIterator.hasNext()) inputIterator.next() // empty line
        }

        numbersToDraw.forEach { number ->
            val winningScore = boards.markBoards(number)
            if (winningScore != null) {
                return winningScore
            }
        }

        return "No winner!"
    }

    class BingoBoards {
        private val boards: MutableList<BingoBoard> = mutableListOf()
        private val lookupMap: MutableMap<UInt, MutableList<BingoBoardRef>> = mutableMapOf()

        fun addBoard(board: BingoBoard) {
            boards.add(board)
            board.lookupMap.forEach { (number: UInt, ref: BingoBoardRef) ->
                lookupMap.computeIfAbsent(number) { mutableListOf() }.add(ref)
            }
        }

        fun markBoards(number: UInt): UInt? {
            val boardRefsToMark = lookupMap.getOrElse(number) { listOf() }
            boardRefsToMark.forEach { ref ->
                val isWinner = ref.board.mark(number, ref)
                if (isWinner) {
                    return number * ref.board.sumOfUnmarkedNumbers
                }
            }

            return null
        }
    }

    class BingoBoard(rawRows: List<String>) {
        companion object {
            const val NUM_MARKS_TO_WIN = 5
        }

        var sumOfUnmarkedNumbers = 0u
        val lookupMap = mutableMapOf<UInt, BingoBoardRef>()
        private val rowSums = Array(5) { 0 }
        private val columnSums = Array(5) { 0 }

        init {
            rawRows.forEachIndexed { rowIndex: Int, line: String ->
                line.trim().split(SUBSEQUENT_WHITESPACE_REGEX).forEachIndexed { columnIndex: Int, rawNumber: String ->
                    val number = rawNumber.toUInt()
                    lookupMap[number] = BingoBoardRef(this, rowIndex, columnIndex)
                    sumOfUnmarkedNumbers += number
                }
            }
        }

        fun mark(number: UInt, ref: BingoBoardRef): Boolean {
            sumOfUnmarkedNumbers -= number
            val isWinnerByRow = (++rowSums[ref.rowIndex] == NUM_MARKS_TO_WIN)
            val isWinnerByColumn = (++columnSums[ref.columnIndex] == NUM_MARKS_TO_WIN)
            return isWinnerByRow || isWinnerByColumn
        }
    }

    data class BingoBoardRef(val board: BingoBoard, val rowIndex: Int, val columnIndex: Int)
}
