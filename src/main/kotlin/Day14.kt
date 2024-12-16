class Day14 {
    val input = readInput(14)

    fun part1(): Int {
        val robots = parseInput()
        robots.forEach { it.move(100) }
        val s = Space(101, 103)
        return s.robotsPerQuadrant(robots)
    }

    fun part2(): Int {
        val s = Space(101, 103)
        val robots = parseInput()

        for(i in 1..100000) {
            robots.forEach { it.move(1) }
            val endPositions = robots.map { it.position }.map{ Coords2D(moduloNeg(it.x, s.width), moduloNeg(it.y, s.height))}
            if(endPositions.groupBy { it.x }.values.any { it.size > 34 }  && endPositions.groupBy { it.y }.values.any { it.size > 30 } ) {
                s.printTree(endPositions.toSet())
                return i
            }
        }
        return 0
    }

    fun parseInput(): List<RobotVector> {
        val vector = """(-?\d+)""".toRegex()
        val robots = input.map {
                    val coords = vector.findAll(it).map(MatchResult::value)
                        .map { content -> content.toInt() }
                        .toList()
                    RobotVector(coords[0], coords[1], coords[2], coords[3])
        }
        return robots
    }
}

class RobotVector(var position: Coords2D, val velocity: Coords2D) {
    constructor(px : Int, py : Int, vx : Int, vy : Int) : this(Coords2D(px, py), Coords2D(vx, vy))
    fun move(count: Int) {
        for (i in 1..count) {
            position += velocity
        }
    }
}

data class Quadrant(val minx : Int, val maxx : Int, val miny : Int, val maxy : Int) {
}

class Space(val width : Int, val height : Int) {
    val quadrants = split()

    fun robotsPerQuadrant(robots :List<RobotVector>) : Int {
        val endPositions = robots.map { it.position }.map{ Coords2D(moduloNeg(it.x, width), moduloNeg(it.y, height))}
        return quadrants.map { q -> endPositions.filter { inQuadrant(it, q) }.size }.multiply()
    }

    private fun inQuadrant(robotPosition: Coords2D, quadrant: Quadrant): Boolean {
        val x = robotPosition.x
        val y = robotPosition.y
        return x >= quadrant.minx && x<= quadrant.maxx
                && y >= quadrant.miny && y <= quadrant.maxy
    }

    fun split() : List<Quadrant> {
        val middlew = width / 2
        val midlleh = height / 2
        return listOf(
            Quadrant(0, middlew-1, 0, midlleh-1),
            Quadrant(middlew+1, width-1, 0, midlleh-1),
            Quadrant(0, middlew-1, midlleh+1, height-1),
            Quadrant(middlew+1, width-1, midlleh+1, height-1))
    }

    fun printTree(positions: Set<Coords2D>) {
        for(y in 0..<height) {
            for(x in 0..<width) {
                if(positions.contains(Coords2D(x, y))) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }

    }
}
fun moduloNeg(num: Int, size: Int): Int {
    return if (num < 0) {
        (size + (num % size)) % size
    } else {
        num % size
    }
}

