import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day06Test {
    private val day06 = Day06()

    @Test
    fun testPart1() {
        assertEquals(4696, day06.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(1443, day06.part2())
    }

}