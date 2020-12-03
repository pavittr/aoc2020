import java.io.File
import java.nio.charset.Charset

class Day1 {

    fun makeItSo() {
        val numArray = File("puzzles/day1").readLines(Charset.defaultCharset()).map { it.toInt() }.sorted()
        val smaller = numArray.filter { it < 1010 }
        val larger = numArray.filter { it > 1010 }
        if (numArray.count { it == 1010 }  == 2) {
            println("We have two perfect matches")
            return
        }

        val upperPart = smaller.map { 2020 - it }.first { larger.contains(it) }
        println("Part 1: " + (upperPart * (2020 - upperPart)))

        /// Part 2

        // firstPassMade are all the remainders that need to be found using two other numbers
        // Essentially this is now the calculation above, but the target isn't consistently 2020 it varies
        val smallNumberDiffs = smaller.map { 2020 - it }
        for (smallNumberDiff in smallNumberDiffs) {
            val bigMatchArray = smaller.map { smallNumberDiff - it }.filter { numArray.contains(it) }
            if (bigMatchArray.isNotEmpty()) {
                val bigMatch = bigMatchArray.first()
                val lastNum = smallNumberDiff - bigMatch
                val firstNum = 2020 - smallNumberDiff
                println("Part 2: $firstNum, $bigMatch, $lastNum")
                println("Part 2: " + (firstNum * bigMatch * lastNum))
                return
            }
        }
        println("Part 2: No match found")
    }

}