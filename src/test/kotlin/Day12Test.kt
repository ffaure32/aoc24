import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day12Test {
    private val day12 = Day12()

    @Test
    fun testPart1() {
        assertEquals(1533644, day12.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(936718, day12.part2())
    }

}