class Day15 {
    val input = readInput(15)

    fun part1(): Long {
        val warehouse = parseInput()
        warehouse.allMoves()
        return warehouse.gps()
    }

    fun part2(): Long {
        val warehouse = parseInputSecond()
        warehouse.allMoves()
        return warehouse.gps()
    }

    fun parseInput(): Warehouse {
        val split = input.indexOf("")
        val directions = input.drop(split)
        val map = input.take(split)
        val directionsList = directions.flatMap {
            it.map { c ->
                when (c) {
                    'v' -> Directions.DOWN
                    '<' -> Directions.LEFT
                    '>' -> Directions.RIGHT
                    '^' -> Directions.UP
                    else -> throw IllegalArgumentException()
                }
            }
        }
        val boxes = mutableSetOf<Coords2D>()
        var submarine = Coords2D(0,0)
        val walls = mutableSetOf<Coords2D>()
        map.indices.forEach { x ->
            map.indices.forEach { y ->
                val coords = Coords2D(x, y)
                when(value(coords)) {
                    '#' -> walls.add(coords)
                    '@' -> {
                        submarine = coords
                    }
                    'O' -> boxes.add(coords)
                    else -> Unit
                }
            }
        }
        return Warehouse(boxes, submarine, walls, directionsList)
    }

    fun parseInputSecond(): SecondWarehouse {
        val split = input.indexOf("")
        val directions = input.drop(split)
        val map = input.take(split)
        val directionsList = directions.flatMap {
            it.map { c ->
                when (c) {
                    'v' -> Directions.DOWN
                    '<' -> Directions.LEFT
                    '>' -> Directions.RIGHT
                    '^' -> Directions.UP
                    else -> throw IllegalArgumentException()
                }
            }
        }
        val boxes = mutableSetOf<Box>()
        var submarine = Coords2D(0,0)
        val walls = mutableSetOf<Coords2D>()
        map.indices.forEach { x ->
            map.indices.forEach { y ->
                val coords = Coords2D(2*x, y)
                val next = Coords2D(2*x+1, y)
                when(value(Coords2D(x, y))) {
                    '#' ->  {
                        walls.add(coords)
                        walls.add(next)
                    }
                    '@' -> {
                        submarine = coords
                    }
                    'O' -> {
                        boxes.add(Box(coords, next))
                    }
                    else -> Unit
                }
            }
        }
        return SecondWarehouse(boxes, submarine, walls, directionsList)
    }

    fun value(coords : Coords2D): Char {
        return input[coords.y][coords.x]
    }

}
class Warehouse(
    val boxes: MutableSet<Coords2D>,
    var submarine: Coords2D,
    val walls: Set<Coords2D>,
    val directionsList: List<Directions>
) {
    fun gps() : Long {
        return boxes.sumOf {
            it.x + it.y * 100L
        }
    }

    fun allMoves() {
        directionsList.forEach {
            move(it)
        }
    }
    fun move(direction : Directions) {
        val first = direction.move(submarine)
        var next = first
        while(boxes.contains(next)) {
            next = direction.move(next)
        }
        if (!walls.contains(next)) {
            if(boxes.contains(first)) {
                boxes.remove(first)
                boxes.add(next)
            }
            submarine = first
        }
    }

    fun printMap() {
        val allCoords = boxes+walls+submarine
        val maxX = allCoords.maxOf { it.x }
        val maxY = allCoords.maxOf { it.y }
        for(y in 0..maxY) {
            for(x in 0..maxX) {
                when {
                    walls.contains(Coords2D(x, y)) -> {
                        print("#")
                    }
                    boxes.contains(Coords2D(x, y)) -> {
                        print("O")
                    }
                    submarine == Coords2D(x, y) -> {
                        print("@")
                    }
                    else -> {
                        print('.')
                    }
                }
            }
            println()
        }
    }
}

data class Box(val left : Coords2D, val right : Coords2D)

class SecondWarehouse(
    val boxes: MutableSet<Box>,
    var submarine: Coords2D,
    val walls: Set<Coords2D>,
    val directionsList: List<Directions>
) {
    fun gps() : Long {
        return boxes.map { it.left }.sumOf {
            it.x + it.y * 100L
        }
    }

    fun allMoves() {
        directionsList.forEach {
            move(it)
        }
    }

    fun move(direction : Directions) {
        val first = direction.move(submarine)
        var next = first
        val toMove = mutableSetOf<Box>()
        val allBoxesCoords = boxes.flatMap { setOf(it.left, it.right) }
        if(HORIZONTAL_DIRECTIONS.contains(direction)) {
            while (allBoxesCoords.contains(next)) {
                next = direction.move(next)
                toMove.addAll(boxes.filter { it.left == next || it.right == next })
                next = direction.move(next)
            }
            if (!walls.contains(next)) {
                if (allBoxesCoords.contains(first)) {
                    boxes.addAll(toMove.map { Box(direction.move(it.left), direction.move(it.right)) })
                    boxes.removeAll(toMove)
                }
                submarine = first
            }
        } else {
            var nextStep = mutableSetOf<Coords2D>()
            nextStep.add(first)
            while(allBoxesCoords.intersect(nextStep).isNotEmpty()
                && walls.intersect(nextStep).isEmpty()) {
                val nextBoxes = boxes.filter { setOf(it.left, it.right).intersect(nextStep).isNotEmpty() }
                toMove.addAll(nextBoxes)
                nextStep = nextBoxes.flatMap { setOf(direction.next(it.left), direction.move(it.right)) }.toMutableSet()
            }
            if(walls.intersect(nextStep).isEmpty()) {
                boxes.removeAll(toMove)
                boxes.addAll(toMove.map { Box(direction.move(it.left), direction.move(it.right)) })
                submarine = first
            }
        }
    }


    fun printMap() {
        val flatBoxLeft = boxes.map { it.left }
        val flatBoxRight = boxes.map { it.right }
        val allCoords = flatBoxLeft +flatBoxRight +walls+submarine
        val maxX = allCoords.maxOf { it.x }
        val maxY = allCoords.maxOf { it.y }
        for(y in 0..maxY) {
            for(x in 0..maxX) {
                when {
                    walls.contains(Coords2D(x, y)) -> {
                        print("#")
                    }
                    flatBoxLeft.contains(Coords2D(x, y)) -> {
                        print("[")
                    }
                    flatBoxRight.contains(Coords2D(x, y)) -> {
                        print("]")
                    }
                    submarine == Coords2D(x, y) -> {
                        print("@")
                    }
                    else -> {
                        print('.')
                    }
                }
            }
            println()
        }
    }
}
