import java.util.*

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
    val startNode = Node(start, Directions.RIGHT)
    val shortestDistances = dijkstraWithLoops(buildWeightedNodes(), startNode)
    val weightedNodes = buildWeightedNodes()

    fun solve(): Int {
        return SQUARED_DIRECTIONS.map { Node(end, it) }.minOf { getDistance(it) }
    }

    fun shortestPath() : Int {
        val lastNode = SQUARED_DIRECTIONS.map { Node(end, it) }.minBy { getDistance(it) }
        val target = getDistance(lastNode)
        val validPath = setOf(Pair(lastNode, target))

        val validCoords = mutableSetOf(lastNode.coords)

        return nextSteps(validPath, validCoords)
    }

    private fun nextSteps(currentValues: Set<Pair<Node, Int>>, validCoords: MutableSet<Coords2D>): Int {
        currentValues.forEach {
            cv ->
                val validOrigins = weightedNodes.filter {
                    l -> l.value.any{ it.first == cv.first && cv.second == getDistance(l.key) + it.second }
                }
                val nextValues = validOrigins.keys.map { k -> Pair(k, getDistance(k)) }.toSet()
                validCoords.addAll(nextValues.map { it.first.coords })
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
        val weightedNodes = mutableMapOf<Node, List<Pair<Node, Int>>>()
        path.forEach { p ->
            val associatedNodes = SQUARED_DIRECTIONS.map { d -> Node(p, d) }
            val anMap = associatedNodes.associateWith { an ->
                val pairs = an.turns().map { Pair(it, 1000) }.toMutableList()
                val next = an.neighbour()
                if (path.contains(next.coords)) {
                    pairs.add(Pair(next, 1))
                }
                pairs
            }
            weightedNodes.putAll(anMap)
        }
        return weightedNodes.toMap()
    }
}


fun dijkstraWithLoops(graph: Map<Node, List<Pair<Node, Int>>>, start: Node): Map<Node, Int> {
    val distances = mutableMapOf<Node, Int>().withDefault { Int.MAX_VALUE }
    val priorityQueue = PriorityQueue<Pair<Node, Int>>(compareBy { it.second })
    val visited = mutableSetOf<Pair<Node, Int>>()

    priorityQueue.add(start to 0)
    distances[start] = 0

    while (priorityQueue.isNotEmpty()) {
        val (node, currentDist) = priorityQueue.poll()
        if (visited.add(node to currentDist)) {
            graph[node]?.forEach { (adjacent, weight) ->
                val totalDist = currentDist + weight
                if (totalDist < distances.getValue(adjacent)) {
                    distances[adjacent] = totalDist
                    priorityQueue.add(adjacent to totalDist)
                }
            }
        }
    }
    return distances
}

data class Node(val coords : Coords2D, val direction : Directions) {
    fun neighbour(): Node {
        return Node(direction.next(coords), direction)
    }
    fun turns(): List<Node> {
        return listOf(Node(coords, direction.turnRight()), Node(coords, direction.turnLeft()))
    }
}