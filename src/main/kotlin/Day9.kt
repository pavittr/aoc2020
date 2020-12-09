import java.io.File
import java.math.BigInteger
import java.nio.charset.Charset

fun main() {
    val testDocs = File("test/day9").readLines(Charset.defaultCharset()).map{it.toBigInteger()}
    val puzzles = File("puzzles/day9").readLines(Charset.defaultCharset()).map{it.toBigInteger()}

    fun lastNumIsValid(input: List<BigInteger>) : Boolean {
        val target = input.last()
        val preamble = input.dropLast(1)
        return preamble.filterIndexed { index, i -> val needed = target - i; preamble.filterIndexed { innerIndex, innerI -> innerIndex != index && innerI == needed }.isNotEmpty() }.isNotEmpty()
    }

    println(testDocs.windowed(6,1,false).filter { !lastNumIsValid(it) }.map { it.last() })
    println(puzzles.windowed(26,1,false).filter { !lastNumIsValid(it) }.map { it.last() })

    fun findList(input: List<BigInteger>, target: BigInteger) : List<BigInteger> {
        for (dropFirst in 0..input.size) {
            val firstDrop = input.drop(dropFirst)
            for (dropLast in 0..input.size) {
                val rangeToTest = input.dropLast(dropLast).intersect(firstDrop)

                if (rangeToTest.sumOf {  it } == target) {
                    return rangeToTest.toList()
                }
            }
        }
        return listOf()
    }

    val testRange = findList(testDocs, BigInteger.valueOf(127))
    println(testRange.minOrNull()?.plus(testRange.maxOrNull()!!))
    val realRange = findList(puzzles, BigInteger.valueOf(375054920))
    println(realRange.minOrNull()?.plus(realRange.maxOrNull()!!))
}



