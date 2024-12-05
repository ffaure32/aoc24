class Day05 {
    private val input = readInput(5)
    val manuals = mutableMapOf<Int, SortedManual>()
    val pageNumbers = mutableListOf<PageNumber>()
    fun part1() : Int {
        parseInput()
        return pageNumbers.filter { it.valid(manuals) }.sumOf{ it.middle() }
    }

    fun part2() : Int {
        parseInput()
        return pageNumbers.filter { !it.valid(manuals) }.map{ it.reorder(manuals) }.sumOf{ it.middle() }
    }

    private fun parseInput() {
        input.forEach {
            if(it.contains('|')) {
                val (lower, greater) = it.split("|").map { it.toInt() }
                val greaterManual = manuals.getOrPut(greater, { SortedManual() })
                greaterManual.before.add(lower)
            } else if(it.contains(",")) {
                pageNumbers.add(PageNumber(it.split(",").map { it.toInt() }))
            }
        }
    }
}

class PageNumber(val pages : List<Int>) {
    fun valid(manuals : Map<Int, SortedManual>) : Boolean {
        return pages == this.reorder(manuals).pages
    }

    fun middle(): Int {
        return pages[pages.size/2]
    }

    fun reorder(manuals : Map<Int, SortedManual>) : PageNumber{
        return PageNumber(pages.sortedWith(PageComarator(manuals)))
    }
}

class PageComarator(val manuals: Map<Int, SortedManual>) : Comparator<Int> {
    override fun compare(o1: Int?, o2: Int?): Int {
        val manual = manuals[o1!!]
        return if(manual!!.before.contains(o2!!)) 1 else -1
    }

}

class SortedManual() {
    val before = mutableSetOf<Int>()
}
