import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day16Test {
    private val day16 = Day16()

    @Test
    fun testPart1() {
        assertEquals(88468, day16.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(616, day16.part2())
    }

}