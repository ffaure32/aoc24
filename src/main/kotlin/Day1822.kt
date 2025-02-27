private const val NO_TOOL = 0
private const val TORCH = 1
private const val CLIMBING_GEAR = 2
val tools = setOf(NO_TOOL, TORCH, CLIMBING_GEAR)
private const val ROCKY = 0L
private const val WET = 1L
private const val NARROW = 2L

private const val MARGIN = 50

class Cave(var depth: Int, var target: Coords2D) {
    private val erosionLevels = mutableMapOf<Coords2D, Long>()
    init {
        initErosionLevels()
    }
    private fun initErosionLevels() {
        for (i in 0..target.y+ MARGIN) {
            for (j in 0..target.x+ MARGIN) {
                val position = Coords2D(j, i)
                erosionLevels[position] = erosionLevel(position)
            }
        }
    }

    fun shortestPath(): Long {

        val nodes = mutableMapOf<Node, List<Pair<Node, Int>>>()
        for (i in 0..target.y+ MARGIN) {
            for (j in 0..target.x+ MARGIN) {
                val position = Coords2D(j, i)
                tools.forEach {tool ->
                    val coords3D = position.coords3D(tool)
                    val regionState = erosionLevel(position) % 3
                    if(
                        (tool == NO_TOOL && regionState != ROCKY)
                        || (tool == CLIMBING_GEAR && regionState != NARROW)
                        || (tool == TORCH && regionState != WET)) {
                        val neighbours = mutableListOf<Pair<Node, Int>>()
                        (tools-tool).forEach {o ->
                            neighbours.add(Pair(position.coords3D(o), 7))
                        }
                            SQUARED_DIRECTIONS.map { it.move(position) }.filter { erosionLevels.containsKey(it) }.forEach { neighbours.add(Pair(it.coords3D(tool), 1)) }
                        nodes[coords3D] = neighbours
                    }
                }
            }
        }
        val result = dijkstraWithLoops(nodes, Coords3D(0,0, TORCH))
        return result[target.coords3D(TORCH)]!!.toLong()

    }
    fun erosionLevel(position: Coords2D): Long {
        return (geologicalIndex(position) + depth) % 20183
    }

    fun geologicalIndex(position: Coords2D): Long {
        return geologicalIndex(position.x, position.y)
    }

    fun geologicalIndex(x: Int, y: Int): Long {
        if (x == 0 && y == 0) {
            return 0
        }
        if (x == target.x && y == target.y) {
            return 0
        }
        if (y == 0) {
            return x * 16807L
        }
        if (x == 0) {
            return y * 48271L
        }
        return erosionLevels!![Coords2D(x - 1, y)]!! * erosionLevels!![Coords2D(x, y - 1)]!!
    }

    fun riskLevel(): Long {
        return erosionLevels!!.entries
            .filter { es: Map.Entry<Coords2D, Long> ->
                es.key.x <= target.x
                        && es.key.y <= target.y
            }.map { es: Map.Entry<Coords2D, Long> -> es.value }.map { el: Long -> el % 3 }.sum()
    }
}