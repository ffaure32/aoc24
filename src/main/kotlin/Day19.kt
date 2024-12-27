class Day19 {
    val input = readInput(19)
    val cache = mutableMapOf<String, Long>()
    val patterns = input[0].split(", ")
    val designs = input.drop(2)

    fun part1(): Int {
        return designs.count {
            possible(it) > 0
        }
    }

    fun part2(): Long {
        return designs.sumOf {
            possible(it)
        }
    }

    fun possible(design: String): Long {
        if (design.isEmpty()) {
            return 1
        }
        if (cache.containsKey(design)) {
            return cache.getOrDefault(design, 0)
        }
        val candidates = patterns.filter{design.startsWith(it)}.map { design.drop(it.length) }
        val sumOfPossibles = candidates.sumOf { possible(it) }
        cache[design] = sumOfPossibles
        return sumOfPossibles
    }
}