import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day23Test {
    private val day23 = Day23()
    @Test
    fun testPart1() {
        assertEquals(1098, day23.part1())
    }

    @Test
    fun testPart2() {
        assertEquals("ar,ep,ih,ju,jx,le,ol,pk,pm,pp,xf,yu,zg", day23.part2())
    }

}