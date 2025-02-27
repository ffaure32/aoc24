import javax.swing.text.Position

class Day1823 {
    val input = readInput(1823)
    val nanobots =  input.map{ buildNanoBot(it)}

    fun part1(): Int {
        val strongest = nanobots.maxBy { it.radius }
        return nanobots.count { strongest.inRange(it) }
    }

    private fun buildNanoBot(line : String): NanoBot {
        val split = line.split(", ")
        val coords = split[0].split("=")[1].drop(1).dropLast(1).split(",").map { it.toInt() }
        val radius = split[1].split("=")[1].toLong()
        return NanoBot(Coords3D(coords[0], coords[1], coords[2]), radius)
    }

}

data class NanoBot(val position: Coords3D, val radius: Long) {
    fun inRange(other : NanoBot): Boolean {
        return this.position.distance(other.position) <= this.radius
    }
}