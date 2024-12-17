import org.junit.jupiter.api.Test
import kotlin.math.pow
import kotlin.test.assertEquals

class Day17Test {
    private val day17 = Day17()

    @Test
    fun sampleXor() {
        // IntRange(0, 7).forEach { println(it xor 7) }
        // IntRange(0, 7).forEach { println(it % 8) }
        IntRange(0, 8).forEach {println(((it%8) xor 7) xor (it/(2f.pow((it%8) xor 7))).toInt())}
    }

    @Test
    fun sample1() {
        val c = Computer(longArrayOf(0,0,9), intArrayOf(2,6))
        c.operation(2, 6)
        assertEquals(1, c.registers[1])
    }

    @Test
    fun sample2() {
        val c = Computer(longArrayOf(10,0,0), intArrayOf(5,0,5,1,5,4))
        c.execute()
        assertEquals("0,1,2", c.output.joinToString(","))
    }


    @Test
    fun samplePart2() {
        val c = Computer(longArrayOf(117440,0,0), intArrayOf(0,3,5,4,3,0))
        c.duplicates()
        println(c.output)
    }
    @Test
    fun testPart1() {
        assertEquals("2,1,0,1,7,2,5,0,3", day17.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(267265166222235, day17.part2())
    }

}