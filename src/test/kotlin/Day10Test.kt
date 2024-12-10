import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day10Test {
    private val day10 = Day10()

    @Test
    fun testPart1() {
        assertEquals(822, day10.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(1801, day10.part2())
    }

}