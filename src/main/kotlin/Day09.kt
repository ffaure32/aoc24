class Day09 {
    private val input = readInput(9)
    private val diskMap = parseInput()

    fun part1() : Long {
        diskMap.defragment()
        return diskMap.checksum()

    }

    fun part2() : Long {
        diskMap.fullDefragment()
        return diskMap.fullChecksum()
    }

    private fun parseInput(): DiskMap {
        val mulRegex = """(\d{2})""".toRegex()
        val blocks = mulRegex.findAll(input[0]).map(MatchResult::value)
            .mapIndexed { index, content -> Block(index, content[0].digitToInt(), content[1].digitToInt()) }
            .toMutableList()
        blocks.add(Block(blocks.size, input[0].last().digitToInt(), 0))
        return DiskMap(blocks)
    }
}

class Block(val id : Int, var fileBlocks : Int, val emptySpaces : Int) {
    val otherBlocks = mutableListOf<Block>()
    var spaceLeft = emptySpaces
    fun pickBlock() : Int {
        fileBlocks--
        return id
    }
    fun isEmpty() : Boolean {
        return fileBlocks == 0
    }
    fun fillSpaceWithBlock(other : Block) {
        otherBlocks.add(other)
        spaceLeft -= other.fileBlocks
    }
    fun spaceLeft() : Int {
        return spaceLeft
    }
}

class DiskMap(val blocks: MutableList<Block>) {
    val content = IntArray(blocks.sumOf { it.fileBlocks })
    val fullContent = IntArray(blocks.sumOf { it.fileBlocks+it.emptySpaces })

    fun defragment() {
        var index = 0
        var blockIndex = 0
        while(index<content.size) {
            val first = blocks[blockIndex]
            index += addBlock(first, content, index)
            for(i in 1..first.emptySpaces) {
                if(index>=content.size) {
                    break
                }
                val other = blocks.last()
                content[index] = other.pickBlock()
                if(other.isEmpty()) {
                    blocks.removeLast()
                }
                index++
            }
            blockIndex++
        }
    }

    fun fullDefragment() {
        val mutableBlocks = blocks.toMutableList()
        blocks.asReversed().forEach { block ->
            mutableBlocks.removeLast()
            val freeBlock = mutableBlocks.firstOrNull { it.spaceLeft() >= block.fileBlocks }
            freeBlock?.fillSpaceWithBlock(block)
        }
        var index = 0
        val treatedBlocks = mutableSetOf<Block>()
        blocks.forEach { block ->
            index += if (!treatedBlocks.contains(block)) {
                addBlock(block, fullContent, index)
            } else {
                block.fileBlocks
            }
            block.otherBlocks.forEach {
                index += addBlock(it, fullContent, index)
            }
            treatedBlocks.addAll(block.otherBlocks)
            index += block.spaceLeft()
        }
    }

    fun fullChecksum() : Long {
        return checksum(fullContent)
    }

    fun checksum() : Long {
        return checksum(content)
    }

    private fun checksum(target : IntArray) : Long {
        return target.mapIndexed { index, value -> index*value.toLong() }.sum()
    }

    private fun addBlock(block : Block, target : IntArray, currentIndex : Int): Int {
        for (i in 0..< block.fileBlocks) {
            target[currentIndex+i] = block.id
        }
        return block.fileBlocks
    }

}
