import java.io.File
import java.nio.charset.Charset

class Day1 {

    fun makeItSo() {
            val numArray = File("puzzles/day1").readLines(Charset.defaultCharset()).map { it.toInt()}.sorted()
    val smaller = numArray.filter { it < 1010 }
    val larger = numArray.filter { it > 1010 }
    val exact = numArray.filter { it == 1010 }
    if (exact.size == 2) {
        println("We have two perfect matches")
        return
    }
    val bigMatch = smaller.map { 2020 - it }.first { larger.contains(it) }
    println(bigMatch)
    println("Part 1: " + (bigMatch * (2020-bigMatch)))

    /// Part 2


    // firstPassMade are all the remainders that need to be found using two other numbers
    // Essentially this is now the calculation above, but the target isn't consistently 2020 it varies
    val firstPassMade = smaller.map{ 2020 - it }
    for (firstNum in firstPassMade) {
            val bigMatchArray = smaller.map { firstNum - it }.filter { numArray.contains(it) }
        if (bigMatchArray.isNotEmpty()) {
            val bigMatch = bigMatchArray.first()
            val lastNum = firstNum - bigMatch
            val firstNum = 2020 - firstNum
            println("Part 2: $firstNum, $bigMatch, $lastNum")
            println("Part 2: " + (firstNum* bigMatch* lastNum))
            return
        }
    }



    println("Part 2: No match found")
    }

}