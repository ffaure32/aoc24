class Day06 {
    private val input = readInput(6)

    fun part1() : Int {
        val lab = parseInput()
        lab.moveTillTheEnd()
        return lab.countVisitedPositions()
    }

    fun part2() : Int {
        val lab = parseInput()
        return input.indices.sumOf { x ->
            input.indices.count { y ->
                val newObstacle = Coords2D(x, y)
                val newLab = newLab(lab, newObstacle)
                newLab.inLoop()
            }
        }
    }

    private fun newLab(lab: ManufacturingLab, newObstacle: Coords2D): ManufacturingLab {
        val newLab = ManufacturingLab(lab.guardPosition, lab.walls.plus(newObstacle), input.size)
        newLab.moveTillTheEnd()
        return newLab
    }

    private fun parseInput(): ManufacturingLab {
        val walls = mutableSetOf<Coords2D>()
        val sharpRegex = Regex("#")
        var guardStart : Coords2D? = null
        input.forEachIndexed { y, line ->
            walls.addAll(sharpRegex.findAll(line).map { it.range.first }.map {
                Coords2D(it, y)
            })
            if(line.contains("^")) {
                guardStart = Coords2D(line.indexOf("^"), y)
            }
        }
        return ManufacturingLab(guardStart!!, walls, input.size)
    }
}

class ManufacturingLab(var guardPosition : Coords2D, val walls : Set<Coords2D>, val gridSize : Int) {
    private val visitedPositions = mutableSetOf(guardPosition)
    private var direction = Directions.TOP
    private val visitedOrientedPositions = mutableSetOf(Pair(guardPosition, direction))
    private var loop = false

    fun inside() : Boolean {
        return (guardPosition.inMatrix(gridSize-1))
    }

    fun inLoop() : Boolean {
        return loop
    }

    fun over() : Boolean {
        return !inside() || inLoop()
    }

    fun moveTillTheEnd() {
        while(!over()) {
            moveGuard()
        }
    }

    fun moveGuard() {
        val nextPosition = direction.move(guardPosition)
        if(walls.contains(nextPosition)) {
            direction = direction.turnRight()
        } else {
            guardPosition = nextPosition
            if(inside()) {
                visitedPositions.add(guardPosition)
            }
        }
        loop =  !visitedOrientedPositions.add(Pair(guardPosition, direction))
    }

    fun countVisitedPositions(): Int {
        return visitedPositions.size
    }

}
