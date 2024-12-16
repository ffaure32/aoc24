class Day13 {
    val input = readInput(13)

    fun part1(): Long {
        return input.chunked(4).map { Machine(it) }.sumOf { it.resolve() }
    }

    fun part2(): Long {
        return input.chunked(4).map { Machine(it) }.map{it.pimpMachine()}.sumOf { it.resolve() }
    }
}

class Machine(val buttonA : EquationPart, val buttonB : EquationPart, val total: EquationPart) {
    constructor(info: List<String>) :this(parseButton(info[0]),parseButton(info[1]), parseTotal(info[2]))

    fun pimpMachine() : Machine {
        return Machine(buttonA, buttonB, EquationPart(pimpEquation(total.x), pimpEquation(total.y)))
    }

    fun resolve(): Long {
        val b = (total.x*buttonA.y-total.y*buttonA.x)/(buttonB.x*buttonA.y-buttonB.y*buttonA.x)
        val a =(total.x-buttonB.x*b)/buttonA.x

        return if(isLong(a) && isLong(b)) {
            a.toLong()*3+b.toLong()
        } else {
            0
        }
    }
    private fun isLong(number: Double): Boolean {
        return Math.floor(number) == number
    }
}

fun pimpEquation(init : Double) : Double {
    return init+10000000000000
}

fun parseButton(button: String): EquationPart {
    return parseLine(button, '+')
}

fun parseTotal(total : String): EquationPart {
    return parseLine(total, '=')
}

fun parseLine(line : String, separator : Char): EquationPart {
    val (x, y) = line.split(": ")[1].split(", ")
    return EquationPart(x.substringAfter(separator).toDouble(), y.substringAfter(separator).toDouble())
}

data class EquationPart(val x : Double, val y :Double)

