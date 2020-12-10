import java.io.File
import java.math.BigInteger
import java.nio.charset.Charset

fun main() {
    val testDocs = File("test/day10").readLines(Charset.defaultCharset()).map{it.toBigInteger()}
    val puzzles = File("puzzles/day10").readLines(Charset.defaultCharset()).map{it.toBigInteger()}

    val testEffectiveAdapters = testDocs.union(listOf(BigInteger.ZERO, testDocs.maxOrNull()!!.plus(BigInteger.valueOf(3) ?: BigInteger.valueOf(0)))).toList()
    val effectiveAdapters = puzzles.union(listOf(BigInteger.ZERO, puzzles.maxOrNull()!!.plus(BigInteger.valueOf(3) ?: BigInteger.valueOf(0)))).toList()

    println(testEffectiveAdapters.sorted().windowed(2,1).map { it.last() - it.first() }.groupBy { it }.mapValues { it.value.size }.values.reduce { acc, i -> acc *i })
    println(testEffectiveAdapters.sorted().windowed(2,1).map { it.last() - it.first() }.joinToString("").split("3").filter { it.isNotEmpty() }.filter{it.length > 1}.map{if(it.length == 4) {BigInteger.valueOf(7)} else if (it.length == 3 ) {BigInteger.valueOf(4) } else {BigInteger.TWO} }.reduce{acc, i -> acc.multiply(i)})

    println(effectiveAdapters.sorted().windowed(2,1).map { it.last() - it.first() }.groupBy { it }.mapValues { it.value.size }.values.reduce { acc, i -> acc *i })
    println(effectiveAdapters.sorted().windowed(2,1).map { it.last() - it.first() }.joinToString("").split("3").filter { it.isNotEmpty() }.filter{it.length > 1}.map{if(it.length == 4) {BigInteger.valueOf(7)} else if (it.length == 3 ) {BigInteger.valueOf(4) } else {BigInteger.TWO} }.reduce{acc, i -> acc.multiply(i)})
}



