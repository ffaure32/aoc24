val mulRegex = """(mul)\((\d{1,3}),(\d{1,3})\)""".toRegex()
val doRegex = """(do\(\))""".toRegex()
val dontRegex = """(don't\(\))""".toRegex()

class Day03 {
    private val input = readInput(3).joinToString("")

    fun part1() : Int {
        return sumOfMul(input)
    }

    fun part2() : Int {
        return enabledRanges()
            .map{ input.substring(it.first, it.last) }
            .sumOf { sumOfMul(it) }
    }

    private fun sumOfMul(input: String): Int {
        val all = mulRegex.findAll(input)
        return all.sumOf { it.groupValues[2].toInt() * it.groupValues[3].toInt() }
    }

    private fun enabledRanges() : List<IntRange> {
        val result = mutableListOf<IntRange>()
        val dontRangeStart = dontRegex.findAll(input).map { it.range.first }
        val doRangeStart = doRegex.findAll(input).map { it.range.first }
        var currentDontIndex = dontRangeStart.first()
        result.add(IntRange(0, currentDontIndex))
        while (currentDontIndex < input.length) {
            val start = doRangeStart.firstOrNull { it > currentDontIndex } ?: input.length
            currentDontIndex = dontRangeStart.firstOrNull { it > start } ?: input.length
            if (start < input.length)
                result.add(IntRange(start + 1, currentDontIndex))
        }
        return result

    }
}
