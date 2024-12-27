import org.junit.jupiter.api.Test
import kotlin.math.pow
import kotlin.test.assertEquals

class Day18Test {
    private val day18 = Day18()

    @Test
    fun testPart1() {
        assertEquals(290, day18.part1())
    }

    @Test
    fun testPart2() {
        assertEquals("64,54", day18.part2())
    }

}