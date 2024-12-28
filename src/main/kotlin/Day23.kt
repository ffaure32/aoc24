class Day23 {
    val input = readInput(23)

    fun part1(): Int {
        val parseInput = parseInput()
        val exactThrees = parseInput.filter { it.size == 3 }
        val combi = parseInput.filter { it.size>3 }.flatMap {
            uglySubset(it.toList())
        }
        val threes = (exactThrees+combi).toSet()
        return threes.count { it.any { it.startsWith("t") } }
    }

    fun part2(): String {
        val parseInput = parseInput()
        val max = parseInput.maxBy { it.size }
        val sorted = max.sorted()
        return sorted.joinToString(",")
    }

    fun parseInput(): List<Set<String>> {
        val pairs = input.map {
            val (left, right) = it.split("-")
            setOf(left, right)
        }
        return buildTierces(pairs)
    }

    private fun buildTierces(pairs: List<Set<String>>): MutableList<MutableSet<String>> {
        val tierces = mutableListOf<MutableSet<String>>()
        pairs.forEach { p ->
            val left = p.first()
            val right = p.last()
            val leftMap = tierces
                .filter { it.contains(left) }
                .filter { lm ->
                    lm.all { pairs.contains(setOf(right, it)) }
                } + tierces
                .filter { it.contains(right) }
                .filter { lm ->
                    lm.all { pairs.contains(setOf(left, it)) }
                }
            if (leftMap.isEmpty()) {
                tierces.add(p.toMutableSet())
            } else {
                leftMap.forEach { it.addAll(p) }
            }
        }
        return tierces
    }
}
fun uglySubset(bigList : List<String>) : Set<Set<String>> {
    val result = mutableSetOf<Set<String>>()
    for(i in 0..<bigList.size-2) {
        for(j in i+1..<bigList.size-1) {
            for (k in j + 1..<bigList.size) {
                result.add(setOf(bigList[i], bigList[j], bigList[k]))
            }
        }
    }
    return result
}



