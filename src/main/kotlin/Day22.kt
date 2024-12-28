class Day22 {
    val input = readInput(22)

    fun part1(): Long {
        return input.map { SecretNumber(it.toLong()) }
            .sumOf {
                it.transformations(2000)
                it.secret
            }
    }

    fun part2(): Int {
        val secrets = input.map { SecretNumber(it.toLong()) }
            .map {
                it.transformations(2000)
                it
            }
        val sequences =  secrets.flatMap {
            it.findAllToMaxSequences()
        }.toSet()
        println(sequences.size)
        return sequences
            .map { seq ->
                secrets.sumOf { s -> s.applySequence(seq) }
        }.max()
    }

}

class SecretNumber(var secret : Long) {
    val prices = mutableListOf(price())
    val diffs =  mutableListOf<Int>()
    fun transformations(count : Int) {
        for(i in 1..count) {
            sequence()
            prices.add(price())
            diffs.add(prices[i]-prices[i-1])
        }
    }

    fun applySequence(sequence : List<Int>) : Int {
        for(i in 0..diffs.size-4) {
            if(diffs.subList(i, i+4) == sequence) {
                return prices[i+4]
            }
        }
        return 0
    }

    fun findAllToMaxSequences() : List<List<Int>> {
        val max = prices.max()
        val maxIndexes = prices.mapIndexedNotNull{ index, elem -> index.takeIf{ index >= 4 && elem == max } }
        val toSet = maxIndexes.map { diffs.subList(it - 4, it) }.toSet()
        return toSet.toList()
    }

    fun price() : Int {
        return (secret % 10).toInt()
    }
    fun sequence() {
        mix(secret * 64)
        prune()
        mix(secret / 32)
        prune()
        mix(secret * 2048)
        prune()
    }

    fun mix(toMix : Long) {
        secret = toMix xor secret
    }

    fun prune() {
        secret = secret % 16777216
    }

}

