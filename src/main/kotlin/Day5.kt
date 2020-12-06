import java.io.File
import java.nio.charset.Charset

fun main() {
    println(File("test/day5").readLines(Charset.defaultCharset()).map{input -> input.map { if (it == 'F' || it == 'L') "0" else "1"  }.joinToString (separator = "").let { it.take(7).toInt(2) * 8 + it.drop(7).toInt(2) }}.maxOrNull())
    println(File("puzzles/day5").readLines(Charset.defaultCharset()).map{input -> input.map { if (it == 'F' || it == 'L') "0" else "1"  }.joinToString (separator = "").let { it.take(7).toInt(2) * 8 + it.drop(7).toInt(2) }}.maxOrNull())
    val numbers = File("puzzles/day5").readLines(Charset.defaultCharset()).map{input -> input.map { if (it == 'F' || it == 'L') "0" else "1"  }.joinToString (separator = "").let { it.take(7).toInt(2) * 8 + it.drop(7).toInt(2) }}
    println(IntRange(numbers.minOrNull()?:0,numbers.maxOrNull()?:0).filter{!numbers.contains(it)})


    println(File("test/day5").readLines(Charset.defaultCharset()).map{ it.replace(Regex("[FL]"), "0").replace(Regex("[BR]"), "1").toInt(2) })

}