class Day11 {
    private val input = readInput(11)
    fun part1() : Long {
        return rocksSize(25)
    }

    fun part2() : Long {
        return rocksSize(75)
    }

    private fun rocksSize(loop: Int): Long {
        val rocks = parseInput()
        var rocksByValue = rocks.groupBy { it }
            .map { entry -> entry.key.toLong() to entry.value.count().toLong() }.toMap()
        for (i in 1..loop) {
            val newRocks = mutableMapOf<Long, Long>()
            rocksByValue.forEach { key, value ->
                val newValues = blinkRock(key)
                newValues.forEach {
                    val newValue = newRocks.getOrDefault(it, 0L)+value
                    newRocks[it] = newValue
                }
            }
            rocksByValue = newRocks
        }
        return rocksByValue.values.sum()
    }

    private fun parseInput(): List<Int> {
        return input[0].split(" ").map{it.toInt()}
    }

}



fun blinkRock(value : Long): List<Long> {
    return if (value == 0L) {
        listOf(1L)
    } else if (value.toString().length % 2 == 0) {
        val s = value.toString()
        val l = s.length / 2
        listOf(s.take(l).toLong(), s.drop(l).toLong())
    } else {
        listOf(value * 2024L)
    }
}
