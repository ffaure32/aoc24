class Day17 {
    val input = readInput(17)
    private var computer = parseInput()
    private val duplicatesInput = mutableSetOf<Long>()
    fun part1(): String {
        computer.execute()
        return computer.output.joinToString(",")
    }

    fun part2(): Long {
        findNext(computer.instructions.size-1, 0)
        return duplicatesInput.min()
    }

    fun findNext(nextTargetIndex : Int, currentA : Long) {
        if(nextTargetIndex>=0) {
            LongRange(currentA, currentA + 7).forEach {
                computer = Computer(longArrayOf(it, 0, 0), computer.instructions)
                computer.duplicates()
                if(computer.copyOfInstructions) {
                    duplicatesInput.add(computer.originalRegister)
                }
                if (computer.output[0] == computer.instructions[nextTargetIndex].toLong()) {
                    findNext(nextTargetIndex - 1, it * 8)
                }
            }
        }
    }
    fun parseInput() : Computer {
        val registers = input.subList(0, 3).map { it.split(": ")[1].toLong() }.toLongArray()
        val instructions = input[4].split(": ")[1].split(",").map{ it.toInt()}.toIntArray()
        return Computer(registers, instructions)
    }
}

class Computer(var registers : LongArray, val instructions : IntArray) {
    var instructionPointer = 0
    val originalRegister = registers[0]
    val output = mutableListOf<Long>()
    var copyOfInstructions = false
    val expectedOutput = instructions.toList().map{ it.toLong()}
    fun execute() {
        while (instructionPointer<instructions.size) {
            operation(instructions[instructionPointer], instructions[instructionPointer+1])
            instructionPointer+=2
        }
    }

    fun duplicates() {
        while (instructionPointer<instructions.size && output != expectedOutput) {
            operation(instructions[instructionPointer], instructions[instructionPointer+1])
            instructionPointer+=2
        }
        copyOfInstructions = (output == expectedOutput)
    }

    fun literalOperand(operand : Int): Int {
        return operand
    }

    fun comboOperand(operand : Int) : Long {
        return when {
            operand < 4 -> {
                operand.toLong()
            }
            operand < 7 -> {
                registers[operand -4]
            }
            else -> {
                error("invalid operand value $operand")
            }
        }
    }

    fun operation(operator : Int, operand : Int) {
        when (operator) {
            0 -> {//adv
                registers[0] = (registers[0] / Math.pow(2.toDouble(), comboOperand(operand).toDouble())).toLong()
            }
            1 -> {//bxl
                registers[1] = registers[1] xor literalOperand(operand).toLong()
            }
            2 -> {//bst
                registers[1] = comboOperand(operand) % 8
            }
            3 -> {//jnz
                if(registers[0] != 0L) {
                    instructionPointer = literalOperand(operand)
                    instructionPointer-=2
                }
            }
            4 -> {//bxc
                registers[1] = registers[1] xor registers[2]
            }
            5 -> {//out
                output.add(comboOperand(operand) % 8)
            }
            6 -> {//bdv
                registers[1] = (registers[0] / Math.pow(2.toDouble(), comboOperand(operand).toDouble())).toLong()
            }
            7 -> {//cdv
                registers[2] = (registers[0] / Math.pow(2.toDouble(), comboOperand(operand).toDouble())).toLong()
            }
            else -> {
                error("Invalid operator $operator")
            }
        }
    }
}