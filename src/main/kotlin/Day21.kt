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
    val numpad = mapOf(
        Pair('0', '0') to "",
        Pair('0', '1') to "^<",
        Pair('0', '2') to "^",
        Pair('0', '3') to "^>",
        Pair('0', '4') to "^^<",
        Pair('0', '5') to "^^",
        Pair('0', '6') to "^^>",
        Pair('0', '7') to "^^^<",
        Pair('0', '8') to "^^^",
        Pair('0', '9') to "^^^>",
        Pair('0', 'A') to ">",

        Pair('1', '0') to ">v",
        Pair('1', '1') to "",
        Pair('1', '2') to ">",
        Pair('1', '3') to ">>",
        Pair('1', '4') to "^",
        Pair('1', '5') to "^>",
        Pair('1', '6') to "^>>",
        Pair('1', '7') to "^^",
        Pair('1', '8') to "^^>",
        Pair('1', '9') to "^^>>",
        Pair('1', 'A') to ">>v",

        Pair('2', '0') to "v",
        Pair('2', '1') to "<",
        Pair('2', '2') to "",
        Pair('2', '3') to ">",
        Pair('2', '4') to "<^",
        Pair('2', '5') to "^",
        Pair('2', '6') to "^>",
        Pair('2', '7') to "<^^",
        Pair('2', '8') to "^^",
        Pair('2', '9') to "^^>",
        Pair('2', 'A') to "v>",

        Pair('3', '0') to "<v",
        Pair('3', '1') to "<<",
        Pair('3', '2') to "<",
        Pair('3', '3') to "",
        Pair('3', '4') to "<<^",
        Pair('3', '5') to "<^",
        Pair('3', '6') to "^",
        Pair('3', '7') to "<<^^",
        Pair('3', '8') to "<^^",
        Pair('3', '9') to "^^",
        Pair('3', 'A') to "v",

        Pair('4', '0') to ">vv",
        Pair('4', '1') to "v",
        Pair('4', '2') to "v>",
        Pair('4', '3') to "v>>",
        Pair('4', '4') to "",
        Pair('4', '5') to ">",
        Pair('4', '6') to ">>",
        Pair('4', '7') to "^",
        Pair('4', '8') to "^>",
        Pair('4', '9') to "^>>",
        Pair('4', 'A') to ">>vv",

        Pair('5', '0') to "vv",
        Pair('5', '1') to "<v",
        Pair('5', '2') to "v",
        Pair('5', '3') to "v>",
        Pair('5', '4') to "<",
        Pair('5', '5') to "",
        Pair('5', '6') to ">",
        Pair('5', '7') to "<^",
        Pair('5', '8') to "^",
        Pair('5', '9') to "^>",
        Pair('5', 'A') to "vv>",

        Pair('6', '0') to "<vv",
        Pair('6', '1') to "<<v",
        Pair('6', '2') to "<v",
        Pair('6', '3') to "v",
        Pair('6', '4') to "<<",
        Pair('6', '5') to "<",
        Pair('6', '6') to "",
        Pair('6', '7') to "<<^",
        Pair('6', '8') to "<^",
        Pair('6', '9') to "^",
        Pair('6', 'A') to "vv",

        Pair('7', '0') to ">vvv",
        Pair('7', '1') to "vv",
        Pair('7', '2') to "vv>",
        Pair('7', '3') to "vv>>",
        Pair('7', '4') to "v",
        Pair('7', '5') to "v>",
        Pair('7', '6') to "v>>",
        Pair('7', '7') to "",
        Pair('7', '8') to ">",
        Pair('7', '9') to ">>",
        Pair('7', 'A') to ">>vvv",

        Pair('8', '0') to "vvv",
        Pair('8', '1') to "<vv",
        Pair('8', '2') to "vv",
        Pair('8', '3') to "vv>",
        Pair('8', '4') to "<v",
        Pair('8', '5') to "v",
        Pair('8', '6') to "v>",
        Pair('8', '7') to "<",
        Pair('8', '8') to "",
        Pair('8', '9') to ">",
        Pair('8', 'A') to "vvv>",

        Pair('9', '0') to "<vvv",
        Pair('9', '1') to "<<vv",
        Pair('9', '2') to "<vv",
        Pair('9', '3') to "vv",
        Pair('9', '4') to "<<v",
        Pair('9', '5') to "<v",
        Pair('9', '6') to "v",
        Pair('9', '7') to "<<",
        Pair('9', '8') to "<",
        Pair('9', '9') to "",
        Pair('9', 'A') to "vvv",

        Pair('A', '0') to "<",
        Pair('A', '1') to "^<<",
        Pair('A', '2') to "<^",
        Pair('A', '3') to "^",
        Pair('A', '4') to "^^<<",
        Pair('A', '5') to "<^^",
        Pair('A', '6') to "^^",
        Pair('A', '7') to "^^^<<",
        Pair('A', '8') to "<^^^",
        Pair('A', '9') to "^^^",
        Pair('A', 'A') to "",
    )
    val arrowPad = mapOf(

        Pair('^', '^') to "",
        Pair('^', '>') to "v>",
        Pair('^', 'v') to "v",
        Pair('^', '<') to "v<",
        Pair('^', 'A') to ">",

        Pair('>', '>') to "",
        Pair('>', 'v') to "<",
        Pair('>', '<') to "<<",
        Pair('>', '^') to "<^",
        Pair('>', 'A') to "^",

        Pair('v', 'v') to "",
        Pair('v', '<') to "<",
        Pair('v', '^') to "^",
        Pair('v', '>') to ">",
        Pair('v', 'A') to "^>",

        Pair('<', '<') to "",
        Pair('<', '^') to ">^",
        Pair('<', '>') to ">>",
        Pair('<', 'v') to ">",
        Pair('<', 'A') to ">>^",

        Pair('A', '^') to "<",
        Pair('A', '>') to "v",
        Pair('A', 'v') to "<v",
        Pair('A', '<') to "v<<",
        Pair('A', 'A') to "",
        )

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
        if (diff.y > 0) {
            nextMoves += ("^".repeat(diff.y))
        }
        if (diff.x > 0) {
            nextMoves += (">".repeat(diff.x))
        }
        if (diff.x < 0) {
            nextMoves += ("<".repeat(-diff.x))
        }
        if (diff.y < 0) {
            nextMoves += ("v".repeat(-diff.y))
        }
        return nextMoves
    }


    private fun moveCoordsBackwards(diff: Coords2D): String {
        var nextMoves = ""
        if (diff.x > 0) {
            nextMoves += (">".repeat(diff.x))
        }
        if (diff.y < 0) {
            nextMoves += ("v".repeat(-diff.y))
        }
        if (diff.x < 0) {
            nextMoves += ("<".repeat(-diff.x))
        }
        if (diff.y > 0) {
            nextMoves += ("^".repeat(diff.y))
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
            return numCache[code]!!
        }
        position='A'
        var nextMoves = ""
        code.forEach {
            nextMoves += numpad[Pair(position, it)]!!
            nextMoves += 'A'
            position = it
        }
        numCache.put(code, nextMoves)
        return nextMoves
    }

    val directionCache = mutableMapOf<Pair<String, Int>, Long>()

    fun directionMoves(code : String, depth: Int) : Long {
        val key = Pair(code, depth)
        if(directionCache.contains(key)) {
            val length = directionCache[key]!!
            return length
        }
        if(depth == 0) {
            return code.length.toLong()
        }
        var moveLength = 0L
        var position = 'A'

        code.forEach {
            val goto = arrowPad[Pair(position, it)]!!+'A'
            moveLength += directionMoves(goto, depth-1)
            position = it
        }
        directionCache.put(key, moveLength)
        return moveLength
    }
}


