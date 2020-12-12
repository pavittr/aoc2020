import java.io.File
import java.nio.charset.Charset
import kotlin.math.absoluteValue
import kotlin.streams.toList

fun main() {
    val testDocs = File("test/day12").readLines(Charset.defaultCharset())
    val puzzles = File("puzzles/day12").readLines(Charset.defaultCharset())

    var initBoat = Triple(0, 0, "E")


    fun compass(facing: String, turn: String, magnitude: Int): String {
        val compassPoints = listOf("N", "E", "S", "W")
        val numberTurns = magnitude / 90
        // if L then negative scroll
        val startIndex = compassPoints.mapIndexed { index, point ->
            if (point == facing) {
                index
            } else {
                -1
            }
        }.filter { it >= 0 }.first()

        println("Turning from $facing through $turn $magnitude $numberTurns $startIndex")
        if (numberTurns == 2) {
            return compassPoints[(startIndex + 2) % 4]
        } else if ((turn == "L" && numberTurns == 1) || (turn == "R" && numberTurns == 3)) {
            return compassPoints[(startIndex + 3) % 4]
        } else if ((turn == "L" && numberTurns == 3) || (turn == "R" && numberTurns == 1)) {
            return compassPoints[(startIndex + 1) % 4]
        }
        return facing
    }

    fun move(boat: Triple<Int, Int, String>, direction: String, magnitude: Int): Triple<Int, Int, String> {
        when (direction) {
            "N" -> return Triple(boat.first, boat.second + magnitude, boat.third)
            "S" -> return Triple(boat.first, boat.second - magnitude, boat.third)
            "E" -> return Triple(boat.first + magnitude, boat.second, boat.third)
            "W" -> return Triple(boat.first - magnitude, boat.second, boat.third)
        }
        return boat
    }

    fun directBoat(boat: Triple<Int, Int, String>, instruction: String): Triple<Int, Int, String> {
        val order = instruction.take(1)
        val magnitude = instruction.drop(1).toInt()
        return if (order in listOf("L", "R")) {
            Triple(boat.first, boat.second, compass(boat.third, order, magnitude))
        } else {
            val direction = if (order == "F") {
                boat.third
            } else {
                order
            }
            move(boat, direction, magnitude)
        }
    }
    testDocs.forEach { println(initBoat); initBoat = directBoat(initBoat, it) }

    println(initBoat)
    println(initBoat.first.absoluteValue + initBoat.second.absoluteValue)


    initBoat = Triple(0, 0, "E")
    puzzles.forEach { println(initBoat); initBoat = directBoat(initBoat, it) }

    println(initBoat)
    println(initBoat.first.absoluteValue + initBoat.second.absoluteValue)


    // Part 2

    fun part2(instructions: List<String>) : Pair<Int, Int> {
        var waypoint = Pair(10, 1)
        var boatPos = Pair(0, 0)

        instructions.forEach {
            println("Entering with $boatPos and $waypoint using $it")
            val instruction = it.take(1)
            val magnitude = it.drop(1).toInt()
            if (instruction in listOf("L", "R")) {
                when (it) {
                    "L90" -> waypoint = Pair(waypoint.second * -1, waypoint.first)
                    "L180" -> waypoint = Pair(waypoint.first * -1, waypoint.second * -1)
                    "L270" -> waypoint = Pair(waypoint.second, waypoint.first * -1)
                    "R90" -> waypoint = Pair(waypoint.second, waypoint.first * -1)
                    "R180" -> waypoint = Pair(waypoint.first * -1, waypoint.second * -1)
                    "R270" -> waypoint = Pair(waypoint.second * -1, waypoint.first)

                }
            } else {
                when (instruction) {
                    "N" -> waypoint = Pair(waypoint.first, waypoint.second + magnitude)
                    "E" -> waypoint = Pair(waypoint.first + magnitude, waypoint.second)
                    "S" -> waypoint = Pair(waypoint.first, waypoint.second - magnitude)
                    "W" -> waypoint = Pair(waypoint.first - magnitude, waypoint.second)
                    "F" -> boatPos =
                        Pair(
                            boatPos.first + (waypoint.first * magnitude),
                            boatPos.second + (waypoint.second * magnitude)
                        )
                }
            }
            println("Exiting with $boatPos and $waypoint using $it")
        }
        return boatPos
    }

    println(part2(testDocs).let{it.second.absoluteValue + it.first.absoluteValue})
    println(part2(puzzles).let{it.second.absoluteValue + it.first.absoluteValue})


}




