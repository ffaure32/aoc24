class Day24 {
    val input = readInput(24)

    fun part1(): Long {
        val device = parseInput()
        return device.compute()
    }
    fun part2(): String {
        val device = parseInput()
        return device.swap()
    }

    fun parseInput(): Device {
        val separator = input.indexOf("")
        val values = input.take(separator).associate {
            val (key, value) = it.split(": ")
            key to ("1" == value)
        }
        val operations = input.drop(separator+1).map {
            val (operation, target) = it.split(" -> ")
            val (left, operand, right) = operation.split(" ")
            Operation(left, right, operand, target)
        }
        return Device(values.toMutableMap(), operations)
    }


}

class Device(val values : MutableMap<String, Boolean>, val operations : List<Operation>) {
    val toCompute = operations.toMutableSet()

    fun swap(): String {
        val targets = toCompute.filter { it.target.startsWith('z') }.sortedBy { it.target }.reversed()
        targets.forEach {
            val index = it.target.substring(1).toInt()
            val print = it.print(operations)
            println(it.target)
            for(i in 0..index) {
                var x = "x"
                if(i<10) x+="0"
                x+=i.toString()
                // println("$x count: ${countOccurrences(print, x)}")
            }
            println(print)
            //println("${it.target} = ${it.print(operations)}")
            println("######################################")
        }
        return ""
    }

    fun operationTree(operation: Operation) {
        println(operation)
        operations.filter{ setOf(operation.left, operation.right).contains(it.target) }.forEach { operationTree(it) }
    }
    fun compute(): Long {
        while(toCompute.filter { it.target.startsWith('z') }.isNotEmpty()) {
            val computable = toCompute.filter { values.contains(it.left) && values.contains(it.right) }
            if(computable.isEmpty()) break
            computable.forEach {
                it.apply(values)
                toCompute.remove(it)
            }
        }
        val booleans = values.filter { it.key.startsWith('z') }.toSortedMap().reversed().values
        val binaryString = booleans.map { if (it) "1" else "0" }.joinToString("")
        return binaryString.toLong(2)
    }
}

val cache = mutableMapOf<String, Set<String>>()
data class Operation(val left : String, val right : String, val operand : String, val target : String) {
    fun apply(values: MutableMap<String, Boolean>) {
        val targetValue = when(operand) {
            "AND" -> values[left]!! && values[right]!!
            "OR" -> values[left]!! || values[right]!!
            "XOR" -> values[left]!! xor values[right]!!
            else -> error("Unexpected value")
        }
        values[target] = targetValue
    }


    fun children(operations : List<Operation>) : Set<String> {
        if(cache.contains(target)) {
            return cache[target]!!
        }
        val result = mutableSetOf<String>()
        children(operations, result)
        cache[target] == result
        return result
    }

    fun children(operations : List<Operation>, targets : MutableSet<String>) {
        if(!targets.contains(left)) {
            targets.add(left)
            val leftOperation = operations.firstOrNull { it.target == left }
            if(leftOperation != null) leftOperation.children(operations, targets)
        }
        if(!targets.contains(right)) {
            targets.add(right)
            val rightOperation = operations.firstOrNull { it.target == right }
            if (rightOperation != null) rightOperation.children(operations, targets)
        }
    }

    fun print(operations : List<Operation>) : String {
        val leftOperation = operations.firstOrNull { it.target == left }
        val rightOperation = operations.firstOrNull { it.target == right }
        val printLeft = if(leftOperation != null) leftOperation.print(operations) else left
        val printRight = if(rightOperation != null) rightOperation.print(operations) else right
        return "$target <- ($printLeft $operand $printRight)"
    }
}
fun countOccurrences(str: String, searchStr: String): Int {
    var count = 0
    var startIndex = 0

    while (startIndex < str.length) {
        val index = str.indexOf(searchStr, startIndex)
        if (index >= 0) {
            count++
            startIndex = index + searchStr.length
        } else {
            break
        }
    }

    return count
}