class Day21 {
    val input = readInput(21)

    fun part1(): Long {
        val robotCommands = parseInput()
        return robotCommands.allMoves()
    }

    fun part2(): Long {
        val robotCommands = parseInput()
        return robotCommands.allMoves(25)
    }

    private fun parseInput(): RobotCommands {
        return RobotCommands(input)
    }

    fun value(coords : Coords2D): Char {
        return input[coords.y][coords.x]
    }
}

class RobotCommands(val codes : List<String>) {
    val numericKeyPad = mapOf(
        '0' to Coords2D(2, 1),
        'A' to Coords2D(3, 1),
        '1' to Coords2D(1, 2),
        '2' to Coords2D(2, 2),
        '3' to Coords2D(3, 2),
        '4' to Coords2D(1, 3),
        '5' to Coords2D(2, 3),
        '6' to Coords2D(3, 3),
        '7' to Coords2D(1, 4),
        '8' to Coords2D(2, 4),
        '9' to Coords2D(3, 4),
        )

    val directionalKeypad = mapOf(
        '<' to Coords2D(1, 1),
        'v' to Coords2D(2, 1),
        '>' to Coords2D(3, 1),
        '^' to Coords2D(2, 2),
        'A' to Coords2D(3, 2)
    )

    private fun moveCoords(diff: Coords2D): String {
        var nextMoves = ""
        if (diff.x > 0) {
            nextMoves += moveY(diff)
            nextMoves += moveX(diff)
        } else {
            nextMoves += moveX(diff)
            nextMoves += moveY(diff)
        }
        return nextMoves
    }

    private fun moveCoordsBackwards(diff: Coords2D): String {
        var nextMoves = ""
        if (diff.x > 0) {
            nextMoves += moveX(diff)
            nextMoves += moveY(diff)
        } else {
            nextMoves += moveY(diff)
            nextMoves += moveX(diff)
        }
        return nextMoves
    }


    private fun moveY(diff: Coords2D): String {
        var nextMoves = ""
        if (diff.y > 0) {
            nextMoves += ("^".repeat(diff.y))
        } else {
            nextMoves += ("v".repeat(-diff.y))
        }
        return nextMoves
    }

    private fun moveX(diff: Coords2D): String {
        var nextMoves = ""
        if (diff.x > 0) {
            nextMoves += (">".repeat(diff.x))
        } else {
            nextMoves += ("<".repeat(-diff.x))
        }
        return nextMoves
    }


    var position = 'A'

    fun allMoves(count : Int = 2): Long {
        return codes.sumOf {
            completeMoves(it, count) * it.substring(0, it.length-1).toLong()
        }
    }
    fun completeMoves(code : String, count : Int) : Long {
        return directionMoves(moves(code), count)
    }

    val numCache = mutableMapOf<String, String>()
    fun moves(code : String): String {
        if(numCache.contains(code)) {
            return numCache.getOrElse(code, {error("impossible")})
        }
        position='A'
        var nextMoves = ""
        code.forEach {
            nextMoves += computePath(position, it)
            nextMoves += 'A'
            position = it
        }
        numCache[code] = nextMoves
        return nextMoves
    }


    val directionCache = mutableMapOf<Pair<String, Int>, Long>()
    fun directionMoves(code : String, depth: Int) : Long {
        val key = Pair(code, depth)
        if(directionCache.contains(key)) {
            return directionCache.getOrElse(key, {error("impossible")})
        }
        if(depth == 0) {
            return code.length.toLong()
        }
        var moveLength = 0L
        var position = 'A'

        code.forEach {
            val goto = computeArrowPath(position, it)+'A'
            moveLength += directionMoves(goto, depth-1)
            position = it
        }
        directionCache[key] = moveLength
        return moveLength
    }

    fun computePath(source: Char, target: Char): String {
        return computeAnyPath(numericKeyPad, source, target)
    }

    fun computeArrowPath(source: Char, target: Char): String {
        return computeAnyPath(directionalKeypad, source, target, 2)
    }

    fun computeAnyPath(keyPad : Map<Char, Coords2D>, source : Char, target : Char, forbiddenY : Int = 1): String {
        val coordsSource = keyPad.getOrElse(source, {error("impossible")})
        val coordsTarget = keyPad.getOrElse(target, {error("impossible")})
        val forbiddenX = 1
        val path = coordsTarget - coordsSource
        return if((coordsSource.x == forbiddenX && coordsTarget.y == forbiddenY)
            || (coordsSource.y == forbiddenY && coordsTarget.x == forbiddenX)) {
            moveCoordsBackwards(path)
        } else {
            moveCoords(path)
        }
    }

}


