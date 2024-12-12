import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {
    private val day11 = Day11()

    @Test
    fun testPart1() {
        assertEquals(188902, day11.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(223894720281135, day11.part2())
    }

}