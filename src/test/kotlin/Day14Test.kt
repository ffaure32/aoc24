import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day14Test {
    private val day14 = Day14()

    @Test
    fun parseRegex() {
        val vector = """(-?\d+)""".toRegex()
        vector.findAll("p=10,3 v=-1,2").map(MatchResult::value)
            .forEach { content -> println(content.toInt())}
    }
    @Test
    fun testPart1() {
        assertEquals(218965032, day14.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(7037, day14.part2())
    }

}