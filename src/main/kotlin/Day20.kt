class Day20 {
    val input = readInput(20)

    fun part1(): Int {
        val map = parseInput()
        return map.shorterPaths(2, 100)
    }

    fun part2(): Int {
        val map = parseInput()
        return map.shorterPaths(20, 100)
    }

    private fun parseInput(): TrackMap {
        val tracks = mutableSetOf<Coords2D>()
        var start = Coords2D(0, 0)
        var end = Coords2D(0, 0)
        val walls = mutableSetOf<Coords2D>()

        input.map {
            input.indices.forEach { x ->
                input.indices.forEach { y ->
                    val coords = Coords2D(x, y)
                    when (value(coords)) {
                        'S' -> {
                            start = coords
                            tracks.add(coords)
                        }

                        'E' -> {
                            end = coords
                            tracks.add(coords)
                        }

                        '.' -> tracks.add(coords)
                        '#' -> walls.add(coords)
                        else -> Unit
                    }
                }
            }
        }
        val map = TrackMap(input.size, tracks, walls, start, end)
        return map
    }

    fun value(coords : Coords2D): Char {
        return input[coords.y][coords.x]
    }
}



data class TrackMap(val height : Int, val tracks : Set<Coords2D>, val walls : Set<Coords2D>, val start : Coords2D, val end : Coords2D) {
    val path = buildPath()

    private fun buildPath(): List<Coords2D> {
        val buildPath = mutableListOf(start)
        var current = start
        while(current != end) {
            current = SQUARED_DIRECTIONS.map { it.next(current) }
                .first { !buildPath.contains(it) && tracks.contains(it) }
            buildPath.add(current)
        }
        return buildPath.toList()
    }

    fun shorterPaths(cheatLength : Int, saves : Int): Int {
        val pairs = tracks.flatMap {
            t -> tracks.filter { t != it && it.distance(t) > 1 && it.distance((t)) <= cheatLength }
            .filter { path.indexOf(t) - path.indexOf(it) >= saves + it.distance(t) }.map { setOf(it, t) }.toSet()
        }
        return pairs.size
    }

}
