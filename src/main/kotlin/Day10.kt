val input = readInput(10)

class Day10 {
    private val topographicMap = parseInput()

    fun part1() : Int {
        return topographicMap.trailheadsScores()
    }

    fun part2() : Int {
        return topographicMap.distinctTrails()
    }

    private fun parseInput(): TopographicMap {
        val trailheads = input.flatMapIndexed { y, line ->
            line.mapIndexed { x, _ -> Coords2D(x, y) }
                .filter { input[it.y][it.x] == '0' }
        }.toSet()
        return TopographicMap(trailheads, input.size-1)
    }
}

class TopographicMap(trailheads: Set<Coords2D>, val maxCoord: Int) {
    private val finishedTrails = mutableSetOf<Trail>()
    init {
        findPath(trailheads)
    }

    fun trailheadsScores(): Int {
        return finishedTrails.map{ Pair(it.trailsteps.first(), it.trailsteps.last())}.toSet().size
    }

    fun distinctTrails() : Int {
        return finishedTrails.size
    }

    private fun findPath(trailheads: Set<Coords2D>) {
        trailheads.map { Trail(listOf(it)) }
            .forEach { t -> nextPath(t) }
    }

    private fun nextPath(trail : Trail) {
        val currentCoord = trail.currentCoord()
        val newTrails = SQUARED_DIRECTIONS.map {
                d -> d.next(currentCoord)
        }.filter {
                c -> c.inMatrix(maxCoord) &&  trail.isClimbing(value(c))
        }.map { trail.newTrail(it) }
        finishedTrails.addAll(newTrails.filter { it.over() })
        newTrails.filter { !it.over() }.forEach { nextPath(it) }
    }

}

class Trail(val trailsteps : List<Coords2D>) {
    private val  currentCoord = trailsteps.last()
    fun over(): Boolean {
        return current() == 9
    }

    fun currentCoord() : Coords2D {
        return currentCoord
    }

    fun isClimbing(next : Char): Boolean {
        return next.digitToInt() == current()+1
    }

    fun newTrail(next: Coords2D) : Trail {
        return Trail(trailsteps+next)
    }

    private fun current() : Int {
        return value(currentCoord).digitToInt()
    }
}

fun value(coords : Coords2D): Char {
    return input[coords.y][coords.x]
}