import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day05Test {
    private val day05 = Day05()

    @Test
    fun testPart1() {
        assertEquals(4569, day05.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(6456, day05.part2())
    }

}