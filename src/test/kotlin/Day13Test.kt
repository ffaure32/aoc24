import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day13Test {
    private val day13 = Day13()

    @Test
    fun testPart1() {
        assertEquals(30413, day13.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(92827349540204, day13.part2())
    }

}