class Day25 {
    val input = readInput(25)

    fun part1(): Int {
        return parseInput()
    }

    fun parseInput(): Int {
        var next = input
        val separator = 7
        val keys = mutableListOf<Key>()
        val locks = mutableListOf<Lock>()
        while(next.isNotEmpty()) {
            val keyOrLock = next.take(separator)
            next = next.drop(separator+1)
            val pins = IntRange(0, keyOrLock[0].length-1).map {
                    i -> keyOrLock.map { it[i] }.count { it == '#' }
            }.toIntArray()
            if(keyOrLock[0].startsWith("#")) {
                locks.add(Lock(pins))
            } else {
                keys.add(Key(pins))
            }
        }
        return locks.sumOf {
            l -> keys.count { !it.overlaps(l) }
        }
    }


}

data class Key(val pins: IntArray) {
    fun overlaps(lock : Lock) : Boolean {
        return pins.mapIndexed { index, value -> value+lock.pins[index] }.any { s -> s>7 }
    }
}
data class Lock(val pins: IntArray)
