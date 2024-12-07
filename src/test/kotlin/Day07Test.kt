import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day07Test {
    private val day07 = Day07()

    @Test
    fun testPart1() {
        assertEquals(850435817339, day07.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(104824810233437, day07.part2())
    }

}