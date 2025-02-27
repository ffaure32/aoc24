class Day1812 {
    private val input = readInput(1812)

    fun part1() : Long {
        val tunnel = Tunnel(input[0].drop("initial state: ".length), input.drop(2))
        for (i in 1..20) {
            tunnel.generation()
            tunnel.print()
            println(tunnel)
        }
        return tunnel.plantScore()
    }

    fun part2() : Long {
        val tunnel = Tunnel(input[0].drop("initial state: ".length), input.drop(2))
        var indexPattern: Long = 0
        val outputPatterns = mutableSetOf<String>()
        val rounds = 50000000000
        for (i in 1..rounds) {
            tunnel.generation()
            val output = tunnel.output()
            if(outputPatterns.contains(output)) {
                indexPattern = rounds - i
                break
            } else {
                outputPatterns.add(tunnel.output())
            }
        }
        val finalIndexPattern = indexPattern
        return tunnel.plantScore(finalIndexPattern)
    }


}

class Tunnel(initialState : String, noteValues : List<String>) {
    var state = initialState.mapIndexed { index, plant -> index.toLong() to (plant == '#') }.toMap()
    val notes = noteValues.map {
        val (key, value) = it.split(" => ")
        (key.map { it == '#' }) to (value == "#")
    }.toMap()

    fun generation() {
        val min = state.keys.min() - 4
        val max = state.keys.max() + 4
        state = LongRange(min, max).map {
            it to notes.getOrDefault(neighbours(it), false)
        }.toMap()
        val plantIndexes = state.filter { it.value }.map { it.key }
        val plantMin = plantIndexes.min()
        val plantMax = plantIndexes.max()
        state = state.filter { it.key in plantMin..plantMax }

    }

    fun neighbours(index : Long): List<Boolean> {
        return LongRange(index-2, index+2).map { state.getOrDefault(it, false) }
    }

    fun plantScore(finalIndexPattern: Long = 0): Long {
        return state.filter { it.value }.map { it.key + finalIndexPattern }.sum()
    }

    fun print() {
        state.forEach { if(it.value) print('#') else print('.') }
    }

    fun output() : String {
        return String(state.map { if(it.value) '#' else '.' }.toCharArray())
    }
}
