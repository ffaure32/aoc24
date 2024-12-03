val mulRegex = """(mul)\((\d{1,3}),(\d{1,3})\)""".toRegex()
val doRegex = """(do\(\))""".toRegex()
val dontRegex = """(don't\(\))""".toRegex()

class Day03 {
    private val input = readInput(3)
    private val joinedInput = input.joinToString("")
    fun part1() : Int {
        return input.sumOf { sumOfMul(it) }
    }

    fun part2() : Int {
        return computeRange().sumOf {
            val toAnalyse = joinedInput.substring(it.first, it.last)
            sumOfMul(toAnalyse)
        }
    }

    private fun sumOfMul(input: String): Int {
        val all = mulRegex.findAll(input)
        return all.sumOf { it.groupValues[2].toInt() * it.groupValues[3].toInt() }
    }

    private fun computeRange() : List<IntRange> {
        val result = mutableListOf<IntRange>()
        val dontRangeStart = dontRegex.findAll(joinedInput).map { it.range.first }
        val doRangeStart = doRegex.findAll(joinedInput).map { it.range.first }
        var currentDontIndex = dontRangeStart.first()
        result.add(IntRange(0, currentDontIndex))
        while (currentDontIndex < joinedInput.length) {
            val start = doRangeStart.firstOrNull { it > currentDontIndex } ?: joinedInput.length
            currentDontIndex = dontRangeStart.firstOrNull { it > start } ?: joinedInput.length
            if (start < joinedInput.length)
                result.add(IntRange(start + 1, currentDontIndex))
        }
        return result

    }
}
