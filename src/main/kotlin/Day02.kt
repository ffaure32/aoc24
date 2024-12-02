import kotlin.math.abs

class Day02 {
    private val input = readInput(2)
    fun part1() : Int {
        val levels = parseInput()
        return levels.count { it.safe() }
    }
    fun part2() : Int {
        val levels = parseInput()
        return levels.count { it.safeByOne() }
    }

    private fun parseInput(): List<Level> {
        val levels = input.map {
            Level(it.split(" ").map(String::toInt))
        }
        return levels
    }
}

data class Level(val levelValues : List<Int>) {
    fun safe() : Boolean {
        return safe(levelValues)
    }

    fun safeByOne() : Boolean {
        if(safe()) {
            return true
        }
        for(i in levelValues.indices) {
            val copy = levelValues.toMutableList()
            copy.removeAt(i)
            if(safe(copy)) {
                return true
            }
        }
        return false
    }

    private fun safe(levels : List<Int>) : Boolean {
        val sign = (levels[1] - levels[0]) > 0
        for(i in 1..<levels.size) {
            val diff = levels[i] - levels[i-1]
            val absDiff = abs(diff)
            if(absDiff <1 || absDiff >3 || sign != (diff > 0)) {
                return false
            }
        }
        return true
    }
}
