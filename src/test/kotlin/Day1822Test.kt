import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day1822Test {

    @Test
    fun erosionLevels() {
        val cave = Cave(510, Coords2D(10, 10))

        assertEquals(510, cave.erosionLevel(Coords2D(0, 0)))
        assertEquals(17317, cave.erosionLevel(Coords2D(1, 0)))
        assertEquals(8415, cave.erosionLevel(Coords2D(0, 1)))
        assertEquals(1805, cave.erosionLevel(Coords2D(1, 1)))
        assertEquals(510, cave.erosionLevel(Coords2D(10, 10)))
    }

    @Test
    fun riskLevel() {
        val cave = Cave(510, Coords2D(10, 10))
        assertEquals(114, cave.riskLevel())
    }

    @Test
    fun riskLevelRealInputGmail() {
        val cave = Cave(8112, Coords2D(13, 743))
        assertEquals(10395, cave.riskLevel())
    }

    @Test
    fun riskLevelRealInput() {
        val cave = Cave(3066, Coords2D(13, 726))
        assertEquals(10115, cave.riskLevel())
    }

    @Test
    fun riskLevelRealInputPart2() {
        val cave = Cave(3066, Coords2D(13, 726))
        assertEquals(990, cave.shortestPath())
    }

    @Test
    fun riskLevelRealInputPart2Gmail() {
        val cave = Cave(8112, Coords2D(13, 743))
        assertEquals(1010, cave.shortestPath())
    }


}