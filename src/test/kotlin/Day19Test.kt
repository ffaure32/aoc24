import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day19Test {
    private val day19 = Day19()

    @Test
    fun testPart1() {
        assertEquals(365, day19.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(730121486795169, day19.part2())
    }

}