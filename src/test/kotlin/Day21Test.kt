import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day21Test {
    private val day21 = Day21()

    @Test
    fun testPart1() {
        assertEquals(138764, day21.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(169137886514152, day21.part2())
    }

}