import kotlin.math.abs

class Day01 {
    private val input = readInput(1)
    fun part1() : Int {
        val (firstList, secondList) = parseInput()
        firstList.sort()
        secondList.sort()
        return firstList.mapIndexed { index, left -> abs(secondList[index] - left) }.sum()
    }
    fun part2() : Int {
        val (firstList, secondList) = parseInput()
        return firstList.sumOf { left -> left * secondList.count { it == left } }
    }

    private fun parseInput(): Pair<MutableList<Int>, MutableList<Int>> {
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()
        input.forEach {
            val (left, right) = it.split("   ").map(String::toInt)
            firstList.add(left)
            secondList.add(right)
        }
        return Pair(firstList, secondList)
    }
}
