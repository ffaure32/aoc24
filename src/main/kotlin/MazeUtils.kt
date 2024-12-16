interface MazeNode

data class Edge(val node1: MazeNode, val node2: MazeNode, val distance: Int)

/**
 * See https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 */
fun findShortestPath(edges: List<Edge>, source: MazeNode, target: MazeNode): ShortestPathResult {

    // Note: this implementation uses similar variable names as the algorithm given do.
    // We found it more important to align with the algorithm than to use possibly more sensible naming.

    val dist = mutableMapOf<MazeNode, Int>()
    val prev = mutableMapOf<MazeNode, MazeNode?>()
    val q = findDistinctMazeNodes(edges)

    q.forEach { v ->
        dist[v] = Integer.MAX_VALUE
        prev[v] = null
    }
    dist[source] = 0

    while (q.isNotEmpty()) {
        val u = q.minByOrNull { dist[it] ?: 0 }
        q.remove(u)

        if (u == target) {
            break // Found shortest path to target
        }
        edges
            .filter { it.node1 == u }
            .forEach { edge ->
                val v = edge.node2
                val alt = (dist[u] ?: 0) + edge.distance
                if (alt < (dist[v] ?: 0)) {
                    dist[v] = alt
                    prev[v] = u
                }
            }
    }

    return ShortestPathResult(prev, dist, source, target)
}

private fun findDistinctMazeNodes(edges: List<Edge>): MutableSet<MazeNode> {
    val nodes = mutableSetOf<MazeNode>()
    edges.forEach {
        nodes.add(it.node1)
        nodes.add(it.node2)
    }
    return nodes
}

/**
 * Traverse result
 */
class ShortestPathResult(val prev: Map<MazeNode, MazeNode?>, val dist: Map<MazeNode, Int>, val source: MazeNode, val target: MazeNode) {

    fun shortestPath(from: MazeNode = source, to: MazeNode = target, list: List<MazeNode> = emptyList()): List<MazeNode> {
        val last = prev[to] ?: return if (from == to) {
            list + to
        } else {
            emptyList()
        }
        return shortestPath(from, last, list) + to
    }

    fun shortestDistance(): Int? {
        val shortest = dist[target]
        if (shortest == Integer.MAX_VALUE) {
            return null
        }
        return shortest
    }
}