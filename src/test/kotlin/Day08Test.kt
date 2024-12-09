import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day08Test {
    private val day08 = Day08()

    @Test
    fun testPart1() {
        assertEquals(220, day08.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(813, day08.part2())
    }

}