import java.io.File
import java.nio.charset.Charset

fun main() {
    val testDocs = File("test/day14").readLines(Charset.defaultCharset())
    val puzzles = File("puzzles/day14").readLines(Charset.defaultCharset())

    fun applyMask(mask: String, number: Long): Long {
        val maskCommands = mask.mapIndexed { index, c -> Pair(index, c) }.filter { it.second != 'X' }
        var returnedNumber = number.toString(2).padStart(36, '0').toCharArray()
        maskCommands.forEach {
            returnedNumber[it.first] = it.second
        }
        return returnedNumber.joinToString("").toLong(2)
    }

    fun part1(input: List<String>) {
        val indexedEntries = input.mapIndexed { index, i -> Pair(index, i) }
        val masks = indexedEntries.filter { it.second.startsWith("mask") }
            .map { Pair(it.first, it.second.substringAfter("mask = ")) }

        val slotInstructions = indexedEntries.filter { it.second.startsWith("mem") }
            .map { Triple(it.first, it.second.drop(4).split("]")[0].toLong(), it.second.split("= ")[1].toLong()) }

        var slotMachine = mutableMapOf<Long, Long>()
        slotInstructions.forEach { instruction ->
            val mask = masks.filter { it.first < instruction.first }.maxByOrNull { it.first }?.second ?: ""
            slotMachine[instruction.second] = applyMask(mask, instruction.third)
        }

        println(slotMachine.values.sum())
    }

    part1(testDocs)
    part1(puzzles)

    fun getSlots(startingIndex: Long, mask: String): List<Long> {
        var maskedIndex = startingIndex.toString(2).padStart(36, '0').toCharArray()

        maskedIndex = maskedIndex.mapIndexed { index, c ->
            when (mask[index]) {
                'X' -> 'X'.toChar()
                '0' -> c.toChar()
                '1' -> '1'.toChar()
                else -> 'X'
            }
        }.toCharArray()

        var returnedArrays = mutableListOf(maskedIndex)
        var nextX = returnedArrays.first().indexOf('X')
        while (nextX >= 0) {
            val initialSize = returnedArrays.size
            for (i in 0..initialSize) {
                val zeroValue = returnedArrays[i]
                zeroValue[nextX] = '0'
                returnedArrays[i] = zeroValue
                val oneValue = zeroValue.clone()
                oneValue[nextX] = '1'
                returnedArrays.add(oneValue)
            }
            nextX = returnedArrays.first().indexOf('X')
        }
        return returnedArrays.map { it.joinToString("").toLong(2) }
    }

    fun part2(input: List<String>) {
        val indexedEntries = input.mapIndexed { index, i -> Pair(index, i) }
        val masks = indexedEntries.filter { it.second.startsWith("mask") }
            .map { Pair(it.first, it.second.substringAfter("mask = ")) }

        val slotInstructions = indexedEntries.filter { it.second.startsWith("mem") }
            .map { Triple(it.first, it.second.drop(4).split("]")[0].toLong(), it.second.split("= ")[1].toLong()) }

        var slotMachine = mutableMapOf<Long, Long>()
        slotInstructions.forEach { instruction ->
            val mask = masks.filter { it.first < instruction.first }.maxByOrNull { it.first }?.second ?: ""
            val affectedSlots = getSlots(instruction.second, mask)
            affectedSlots.forEach { slotMachine[it] = instruction.third }
        }

        println(slotMachine.values.sum())
    }

    val test = listOf(
        "mask = 000000000000000000000000000000X1001X",
        "mem[42] = 100",
        "mask = 00000000000000000000000000000000X0XX",
        "mem[26] = 1"
    )
    part2(test)
    part2(puzzles)

}




