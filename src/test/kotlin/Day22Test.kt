import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day22Test {
    private val day22 = Day22()

    @Test
    fun testTransfos() {
        var s = SecretNumber(15887950)
        s.sequence()
        println(s.secret)
        s = SecretNumber(16495136)
        s.sequence()
        println(s.secret)
        s = SecretNumber(527345)
        s.sequence()
        println(s.secret)
        s = SecretNumber(704524)
        s.sequence()
        println(s.secret)
        s = SecretNumber(1553684)
        s.sequence()
        println(s.secret)
        s = SecretNumber(12683156)
        s.sequence()
        println(s.secret)
        s = SecretNumber(11100544)
        s.sequence()
        println(s.secret)
        s = SecretNumber(12249484)
        s.sequence()
        println(s.secret)
        s = SecretNumber(7753432)
        s.sequence()
        println(s.secret)
        println()
        val sn = SecretNumber(123.toLong())
        for (i in 1..10) {
            sn.sequence()
            println(sn.secret)
        }
    }

    @Test
    fun testPart1() {
        assertEquals(20506453102, day22.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(2423, day22.part2())
    }

}