import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day24Test {
    private val day24 = Day24()
    @Test
    fun testPart1() {
        assertEquals(54715147844840, day24.part1())
    }

    @Test
    fun testPart2() {
        assertEquals("", day24.part2())
    }

    @Test
    fun testSample() {
        var device = day24.parseInput()
        val l = 1L
        val left = l.toString(2)
        val r = 2L
        val right = r.toString(2)
        val values = mapBinaryString(left, 'x') + mapBinaryString(right, 'y')
        println(l+r == device.compute())
        assertEquals(l+r, device.compute())

    }

    @Test
    fun testAddition()  {
        var device = day24.parseInput()
        val l = 123235L
        val left = l.toString(2)
        val r = 147659L
        val right = r.toString(2)
        val values = mapBinaryString(left, 'x') + mapBinaryString(right, 'y')
        println(l+r == device.compute())
        val originalOperations = device.operations
        device.operations.forEach { o ->
            //println("AAAAAAAAAAAAAAAAAA ${o.target}")
            device.operations.map { it.target }.filter{ it != o.target }.forEach {
                //println(it)
                //println(o.target)
                device = Device(values.toMutableMap(), swap(originalOperations, o.target, it))
                if(l+r == device.compute()) println("YAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH $it ${o.target}")

            }
        }
        device = Device(values.toMutableMap(), swap(device.operations, "nvn", "hnn"))

        assertEquals(l+r, device.compute())
    }

    private fun swap(operations: List<Operation>, label1 : String, label2 : String) : List<Operation> {
        val new = operations.toMutableList()
        val op1 = operations.first { it.target == label1 }
        val op2 = operations.first { it.target == label2 }

        new.removeIf { it.target in setOf(label1, label2) }
        new.add(Operation(op1.left, op1.right, op1.operand, label2))
        new.add(Operation(op2.left, op2.right, op2.operand, label1))
        return new
    }
    private fun mapBinaryString(binary : String, prefix : Char) : Map<String, Boolean> {
        val values = mutableMapOf<String, Boolean>()
        for(i in 0..44) {
            val suffix = if(i>=10) i.toString() else "0"+i.toString()
            val label = prefix+suffix
            if(i<binary.length) {
                val value = if(binary.toCharArray()[binary.length-i-1] == '1') true else false
                values.put(label, value)
            } else {
                values.put(label, false)
            }
        }
        return values

    }

}