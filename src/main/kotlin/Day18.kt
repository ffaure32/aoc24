class Day18 {
    val input = readInput(18)
    private val mappedInput = input.map {
        val (x, y) = it.split(",").map { it.toInt() }
        Coords2D(x, y)
    }

    fun part1(): Int {
        val startIndex = 1024
        val result = shortestPath(startIndex)
        return result!!
    }

    fun part2(): String {
        val startIndex = findFirstBlockingTileWithDichotomy()
        return input[startIndex]
    }

    private fun findFirstBlockingTileWithDichotomy(): Int {
        var startIndex = 0
        var endIndex = input.size
        while (endIndex - startIndex > 1) {
            val middle = startIndex + (endIndex - startIndex) / 2
            val result = shortestPath(middle)
            if (result == null) {
                endIndex = middle
            } else {
                startIndex = middle
            }
        }
        return startIndex
    }

    private fun shortestPath(startIndex: Int): Int? {
        val map = mappedInput.subList(0, startIndex).toSet()
        val grid = Grid(70, map)
        val result = grid.shortestPath()
        return result
    }


}

data class Grid(val height : Int, val map : Set<Coords2D>) {
    fun shortestPath(): Int? {
        val result = mutableMapOf<Node, List<Pair<Node, Int>>>()
        for (y in 0..height) {
            for (x in 0..height) {
                val coords = Coords2D(x, y)
                val neighbours = SQUARED_DIRECTIONS
                    .map { it.next(coords) }
                    .filter { it.inMatrix(height) && !map.contains(it) }
                    .map { Pair(it, 1) }.toList()
                result.put(coords, neighbours)
            }
        }
        val dijkstra = dijkstraWithLoops(result, Coords2D(0, 0))
        return dijkstra[Coords2D(height, height)]
    }
}

