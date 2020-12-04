import java.io.File
import java.math.BigInteger
import java.nio.charset.Charset

class Day3 {
    fun makeItSo() {
        println(File("test/day3").readLines(Charset.defaultCharset()).map{ line -> line.map { it == '#' }}.filterIndexed { index, list -> list[index * 3 % list.size] }.count())
        println(File("puzzles/day3").readLines(Charset.defaultCharset()).map{ line -> line.map { it == '#' }}.filterIndexed { index, list -> list[index * 3 % list.size] }.count())

        val gradients= listOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1) , Pair(1, 2))
        println(gradients.map { gradient -> File("test/day3").readLines(Charset.defaultCharset()).map{ line -> line.map { it == '#' }}.filterIndexed { index, list -> (index%gradient.second == 0) && list[(index * gradient.first/gradient.second) % list.size] }.count().toBigInteger() }.reduce { acc, i -> acc * i })
        println(gradients.map { gradient -> File("puzzles/day3").readLines(Charset.defaultCharset()).map{ line -> line.map { it == '#' }}.filterIndexed { index, list -> (index%gradient.second == 0) && list[(index * gradient.first/gradient.second) % list.size] }.count().toBigInteger() }.reduce { acc, i -> acc * i })
    }
}
