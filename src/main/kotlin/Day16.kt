class Day16 {
    val input = readInput(16)
    val maze = parseInput()

    fun part1(): Int {
        return maze.solve()
    }

    fun part2(): Int {
        return maze.shortestPath()
    }

    private fun parseInput(): Maze {
        var start = Coords2D(0,0)
        var end = Coords2D(0,0)
        val path = mutableSetOf<Coords2D>()
        input.indices.forEach { x ->
            input.indices.forEach { y ->
                val coords = Coords2D(x, y)
                when(value(coords)) {
                    'S' ->  {
                        start = coords
                        path.add(coords)
                    }
                    'E' -> {
                        end = coords
                        path.add(coords)
                    }
                    '.' -> path.add(coords)
                    else -> Unit
                }
            }
        }
        return Maze(start, end, path)
    }
    private fun value(coords : Coords2D): Char {
        return input[coords.y][coords.x]
    }

}

class Maze(start: Coords2D, val end: Coords2D, val path: MutableSet<Coords2D>) {
    val startNode = TileNode(start, Directions.RIGHT)
    val shortestDistances = dijkstraWithLoops(buildWeightedNodes(), startNode)
    val weightedNodes = buildWeightedNodes()
    val invertedNodes = invertNodes()

    private fun invertNodes(): Map<Node, List<Node>> {
        val result = mutableMapOf<Node, MutableList<Node>>()
        weightedNodes.forEach { (k, v) -> v.forEach {
                val current = result.getOrPut(it.first, {mutableListOf()})
                current.add(k)
            }
        }
        return result
    }

    fun solve(): Int {
        return SQUARED_DIRECTIONS.map { TileNode(end, it) }.minOf { getDistance(it) }
    }

    fun shortestPath() : Int {
        val lastNode = SQUARED_DIRECTIONS.map { TileNode(end, it) }.minBy { getDistance(it) }
        val target = getDistance(lastNode)
        val validPath = setOf(Pair(lastNode, target))

        val validCoords = mutableSetOf(lastNode.coords)

        return nextSteps(validPath, validCoords)
    }

    private fun nextSteps(currentValues: Set<Pair<Node, Int>>, validCoords: MutableSet<Coords2D>): Int {
        currentValues.forEach {
            cv ->
                val origins = invertedNodes[cv.first]!!
                val validOrigins = origins.filter {
                    l -> weightedNodes[l]!!.any{ it.first == cv.first && cv.second == getDistance(l) + it.second }
                }
                val nextValues = validOrigins.map { k -> Pair(k, getDistance(k)) }.toSet()
                validCoords.addAll(nextValues.map { (it.first as TileNode).coords })
                if(nextValues.isNotEmpty()) {
                    nextSteps(nextValues, validCoords)
                }
        }
        return validCoords.size
    }

    private fun getDistance(node : Node): Int {
        return shortestDistances.getOrDefault(node, Int.MAX_VALUE)
    }

    private fun buildWeightedNodes(): Map<Node, List<Pair<Node, Int>>> {
        val nodes = mutableMapOf<Node, List<Pair<Node, Int>>>()
        path.forEach { p ->
            val associatedTileNodes = SQUARED_DIRECTIONS.map { d -> TileNode(p, d) }
            val anMap = associatedTileNodes.map { an ->
                val pairs = an.turns().map { Pair(it as Node, 1000) }.toMutableList()
                val next = an.neighbour()
                if (path.contains(next.coords)) {
                    pairs.add(Pair(next, 1))
                }
                an as Node to pairs.toList()
            } .toMap()
            nodes.putAll(anMap)
        }
        return nodes.toMap()
    }
}


data class TileNode(val coords : Coords2D, val direction : Directions) : Node {
    fun neighbour(): TileNode {
        return TileNode(direction.next(coords), direction)
    }
    fun turns(): List<TileNode> {
        return listOf(TileNode(coords, direction.turnRight()), TileNode(coords, direction.turnLeft()))
    }
}