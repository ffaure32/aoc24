import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day20Test {
    private val day20 = Day20()

    @Test
    fun testPart1() {
        assertEquals(1343, day20.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(982891, day20.part2())
    }

}