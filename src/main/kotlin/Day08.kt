import java.util.LinkedList

class Day08 {
    private val input = readInput(8)

    fun part1() : Int {
        val roof = parseInput()
        return roof.antinodes(input.size).size
    }

    fun part2() : Int {
        val roof = parseInput()
        return roof.antinodes(input.size, true).size
    }

    private fun parseInput(): Roof {
        val antennas = mutableMapOf<Char, Antennas>()
        input.forEachIndexed {
            y, line ->
                line.forEachIndexed { x, c ->
                    if( c!= '.') {
                        val antenna = antennas.getOrPut(c, {
                            Antennas()
                        })
                        antenna.coords.add(Coords2D(x, y))
                    }
                }
        }

        return Roof(antennas, input.size)
    }
}

class Antennas {
    val coords = mutableSetOf<Coords2D>()

    fun antinodes(limits: Int, full: Boolean): Set<Coords2D> {
        return pairs().flatMap {
            it.antinodes(limits, full)
        }.toSet()
    }

    private fun pairs() : MutableSet<Antinode> {
        val pairs = mutableSetOf<Antinode>()
        val subCoords = LinkedList(coords.toMutableSet())
        while (subCoords.isNotEmpty()) {
            val first = subCoords.removeFirst()
            subCoords.forEach { pairs.add(Antinode(Pair(first!!, it!!))) }
        }
        return pairs
    }
}

data class Antinode(val pair : Pair<Coords2D, Coords2D>) {
    fun antinodes(limits: Int, full: Boolean): Set<Coords2D> {
        val range = if(full) IntRange(1, limits) else IntRange(2, 2)
        val result = mutableSetOf<Coords2D>()
        val diffVector = (pair.second - pair.first)
        for(i in range) {
            val newAntinode = antinodes(diffVector, i, limits)
            if(newAntinode.isEmpty()) {
                break
            }
            result.addAll(newAntinode)
        }
        return result
    }

    private fun antinodes(diffVector: Coords2D, i: Int, limits: Int): List<Coords2D> {
        val power = diffVector * Coords2D(i, i)
        val firstAntinode = pair.first + power
        val secondAntinode = pair.second - power
        return setOf(firstAntinode, secondAntinode).filter { it.inMatrix(limits) }
    }
}
class Roof(val antennas : Map<Char, Antennas>, val mapSize : Int) {
    fun antinodes(limits: Int, full: Boolean = false): Set<Coords2D> {
        return antennas.values.flatMap { it.antinodes(limits, full) }.filter { it.inMatrix(mapSize-1) }.toSet()
    }
}
