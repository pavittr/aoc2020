import java.io.File
import java.nio.charset.Charset

fun main() {

    fun calc(ops: List<String> ) : Long {
        val init = ops.take(1).first().toLong()
        val output = ops.drop(1).windowed(2, 2, false)
            .map { Pair(it[0], it[1].toInt()) }.fold(init) {acc, op ->
                when(op.first) {
                    "+" -> acc + op.second
                    "*" -> acc * op.second
                    else -> 0
                }
            }
        return output
    }

    fun sumUp(input: String, f : (List<String>) -> Long) : Long {
        var runningSum = input
        val regex = Regex("\\(([^()]+)\\)")
        while (regex.find(runningSum) != null) {
            val thisMatch = regex.find(runningSum)
            val replacement = thisMatch?.groupValues?.get(1)?.split(" ")?.let { "" + f(it) } ?: "0"
            if (thisMatch != null) {
                runningSum = runningSum.replaceRange(thisMatch.range, replacement)
            }
        }

        return f(runningSum.split(" "))
    }

    fun part1(inputFile: String) {
        val input = File(inputFile).readLines(Charset.defaultCharset())
        println(input.map { sumUp(it, ::calc) }.sum())
    }

    fun addFirstSumUp(ops:  List<String>) : Long {
        var withPlusses = ops.toMutableList()

        while (withPlusses.contains("+")) {
            val nextPlus = withPlusses.indexOfFirst { it == "+"}
            val firstOp = withPlusses[nextPlus -1].toLong()
            val secondOp = withPlusses[nextPlus + 1].toLong()
            withPlusses.removeAt(nextPlus - 1)
            withPlusses.removeAt(nextPlus - 1)
            withPlusses.removeAt(nextPlus - 1)
            withPlusses.add(nextPlus-1, "" + (firstOp.plus(secondOp)))
        }

        return calc(withPlusses)
    }

    part1("test/day18")
    part1("puzzles/day18")

    fun part2(inputFile: String) {
        val input = File(inputFile).readLines(Charset.defaultCharset())
        input.forEach {
            println(sumUp(it, ::addFirstSumUp))
        }
        println(input.map { sumUp(it, ::addFirstSumUp) }.sum())
    }

    part2("test/day18")
    part2("puzzles/day18")
}
