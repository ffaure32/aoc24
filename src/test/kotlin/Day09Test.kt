import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day09Test {
    private val day09 = Day09()

    @Test
    fun testPart1() {
        assertEquals(6331212425418, day09.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(6363268339304, day09.part2())
    }

}