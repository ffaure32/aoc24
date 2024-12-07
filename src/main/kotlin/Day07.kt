import java.util.LinkedList

class Day07 {
    private val input = readInput(7)

    fun part1() : Long {
        val operators = parseInput()
        return operators.filter { it.testCombinations() }.sumOf { it.testResult }
    }

    fun part2() : Long {
        val operators = parseInput(true)
        return operators.filter { it.testCombinations() }.sumOf { it.testResult }
    }

    private fun parseInput(thirdOperator : Boolean = false): List<BridgetOperator> {
        return input.map {
            val (testResult, operators) = it.split(":")
            val operatorList = operators.trim().split(" ").map { it.toLong() }
            BridgetOperator(testResult.toLong(), LinkedList(operatorList), thirdOperator)
        }
    }
}

data class BridgetOperator(val testResult : Long, val values : LinkedList<Long>, val thirdOperator : Boolean) {
    fun testCombinations() : Boolean {
        val nextValue = values.removeFirst()
        val possibleResults = nextOperation(setOf(nextValue), values)
        return possibleResults.contains(testResult)
    }

    private fun nextOperation(currentResult :Set<Long>, values : LinkedList<Long>) : Set<Long> {
        val nextValue = values.removeFirst()
        val multiply = currentResult.map { it * nextValue }
        val add = currentResult.map { it + nextValue }
        val concat = if(thirdOperator) { currentResult.map{ concatToLong(it, nextValue) }} else { emptySet() }

        val newResult = multiply.union(add).union(concat)
        if(values.isNotEmpty()) {
            return nextOperation(newResult, values)
        }
        return newResult
    }

    private fun concatToLong(left: Long, right: Long) = (left.toString() + right.toString()).toLong()
}
