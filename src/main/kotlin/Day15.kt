import java.io.File
import java.nio.charset.Charset

fun main() {
    val testDocs = File("test/day15").readLines(Charset.defaultCharset())
    val puzzles = File("puzzles/day15").readLines(Charset.defaultCharset())

    fun findLastEntryInGame(input: List<String>, dinnerTime: Int) {
        val numberLine = input[0].split(",").mapIndexed { index, i -> i.toInt() to Pair(index+1,0)  }.toMap().toMutableMap()
        val startLine =  (numberLine.values.maxByOrNull { it.first }?.first ?: 0 )+ 1
        var lastRoundSaid = input[0].split(",").map { it.toInt()}.last()
        for (i in startLine..dinnerTime) {
            val lastPair = numberLine[lastRoundSaid]
            val sayThisTime = if (lastPair?.second ?: 0 == 0) { 0 } else { (lastPair?.first ?: 0) - (lastPair?.second ?: 0 )}
            val pairForThisTime = numberLine[sayThisTime]?: Pair(0,0)
            numberLine[sayThisTime] = Pair(i, pairForThisTime.first)
            lastRoundSaid = sayThisTime
        }
        println(lastRoundSaid)
    }



    findLastEntryInGame(testDocs, 2020)
    findLastEntryInGame(puzzles, 2020)
    findLastEntryInGame(puzzles, 30000000)


}


