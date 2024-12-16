import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day15Test {
    private val day15 = Day15()

    @Test
    fun testPart1() {
        assertEquals(1456590, day15.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(1489116, day15.part2())
    }

}