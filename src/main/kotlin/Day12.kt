class Day12 {
    private val input = readInput(12)
    fun part1(): Long {
        val regions = parseInput()
        return regions.sumOf { r ->
            r.size * perimeter(r)
        }
    }

    fun part2(): Long {
        val regions = parseInput()
        return regions.sumOf { r ->
            r.size * intersections(r)
        }
    }

    fun intersections(r: Set<Coords2D>): Long {
        var cross = countSections(r, HORIZONTAL_DIRECTIONS, yCoords, xCoords)
        cross += countSections(r, VERTICAL_DIRECTIONS, xCoords, yCoords)
        return cross.toLong()
    }

    fun perimeter(region: Set<Coords2D>): Long {
        var cross = countBorders(region, HORIZONTAL_DIRECTIONS, yCoords, xCoords)
        cross += countBorders(region, VERTICAL_DIRECTIONS, xCoords, yCoords)
        return cross.toLong()
    }

    private fun countBorders(region : Set<Coords2D>, directions : List<Directions>, range : (Coords2D) -> Int, key : (Coords2D) -> Int) : Int {
        var cross = 0
        val columnsForBorder = region.map { c -> range(c) }
        val min = columnsForBorder.min()
        val max = columnsForBorder.max()
        for (index in min..max) {
            val onColumn = region.filter { range(it) == index }
                .filter { directions.any { d -> !region.contains(d.next(it)) }}

            val columnNeighbours = onColumn.associate { o ->
                key(o) to directions.map {!region.contains(it.next(o))}
            }
            cross += columnNeighbours.values.sumOf { it.count { it } }
        }
        return cross
    }

    private fun countSections(region : Set<Coords2D>, directions : List<Directions>, range : (Coords2D) -> Int, key : (Coords2D) -> Int) : Int {
        var cross = 0
        val columnsForBorder = region.map { c -> range(c) }
        val min = columnsForBorder.min()
        val max = columnsForBorder.max()
        var previousNeighboursX = mapOf<Int, List<Boolean>>()
        for (index in min..max) {
            val onColumn = region.filter { range(it) == index }
                .filter { directions.any { d -> !region.contains(d.next(it)) }}

            val columnNeighbours = onColumn.associate { o ->
                key(o) to directions.map {!region.contains(it.next(o))}
            }
            cross += compareSteps(columnNeighbours, previousNeighboursX)
            previousNeighboursX = columnNeighbours
        }
        return cross
    }

    val xCoords: (Coords2D) -> Int = { c -> c.x }
    val yCoords: (Coords2D) -> Int = { c -> c.y }

    private fun compareSteps(
        lineNeighbours: Map<Int, List<Boolean>>,
        previousNeighbours: Map<Int, List<Boolean>>
    ) = lineNeighbours.map {
        val previousValue = previousNeighbours.getOrDefault(it.key, listOf(false, false))
        it.value.mapIndexed { index, b -> b && !previousValue[index] }.count { it }
    }.sum()


    fun parseInput(): List<Set<Coords2D>> {
        val allCoordsByLetter = input.flatMapIndexed { y, line ->
            line.mapIndexed { x, _ ->
                Coords2D(x, y)
            }
        }.groupBy { input[it.y][it.x] }
        return allCoordsByLetter.values.flatMap { splitByRegion(it) }
    }

    fun splitByRegion(coords: List<Coords2D>): Set<Set<Coords2D>> {
        val regions = mutableSetOf<Set<Coords2D>>()
        val coordsSet = coords.toMutableSet()
        while (coordsSet.isNotEmpty()) {
            val first = coordsSet.first()
            coordsSet.remove(first)
            val region = mutableSetOf(first)
            var neighbours = findNeighbours(first, region, coordsSet)
            while (neighbours.isNotEmpty()) {
                neighbours = neighbours.flatMap {
                    findNeighbours(it, region, coordsSet)
                }
            }
            regions.add(region)
        }
        return regions.toSet()
    }

    fun findNeighbours(
        neighbour: Coords2D, region: MutableSet<Coords2D>, coordsSet: MutableSet<Coords2D>
    ): List<Coords2D> {
        val newNeighbours = SQUARED_DIRECTIONS.map { it.next(neighbour) }.filter { coordsSet.contains(it) }
            .filter { !region.contains(it) }
        coordsSet.removeAll(newNeighbours)
        region.addAll(newNeighbours)
        return newNeighbours
    }

}

