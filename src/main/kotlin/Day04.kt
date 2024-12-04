class Day04 {
    private val input = readInput(4)
    fun part1() : Int {
        val xList = findInputCoords('X')
        return xList.sumOf { it.countXmas(input) }
    }
    fun part2() : Int {
        val aList = findInputCoords('A')
        return aList.count { it.validCrossmas(input) }
    }

    private fun findInputCoords(searchedChar: Char): List<Xmas> {
        val flatMapIndexed = input.flatMapIndexed { y, line ->
            line.mapIndexed { x, _ ->
                Coords2D(x, y)
            }.filter { input[it.y][it.x] == searchedChar }
        }.map { Xmas(it, input.size) }
        return flatMapIndexed
    }

}

class Xmas(private val x : Coords2D, private val gridSize : Int) {
    fun countXmas(input : List<String>) : Int {
        val mas = charArrayOf('M','A','S')
        return Directions.entries.count { d ->
            var currentPos = x
            val validLetters = mas.filter { l ->
                currentPos = d.next(currentPos)
                (valid(currentPos)
                    && input[currentPos.y][currentPos.x] == l)
            }
            validLetters.size == mas.size
        }
    }

    fun validCrossmas(input : List<String>) : Boolean {
        val ms = setOf('M','S')
        val diag1 = setOf(Directions.TOP_LEFT, Directions.DOWN_RIGHT)
        val diag2 = setOf(Directions.TOP_RIGHT, Directions.DOWN_LEFT)
        return(isMS(diag1, input, ms) && isMS(diag2, input, ms))

    }

    private fun isMS(diag: Set<Directions>, input: List<String>, ms: Set<Char>)
            = diag.map { d -> d.next(x) }.filter { valid(it) }.map { input[it.y][it.x] }.toSet() == ms

    fun valid(coords : Coords2D) : Boolean {
        return coords.valid(gridSize-1, gridSize-1)
    }


}