import java.io.File
import java.nio.charset.Charset
import kotlin.streams.toList

fun main() {
    val testDocs = File("test/day11").readLines(Charset.defaultCharset())
    val puzzles = File("puzzles/day11").readLines(Charset.defaultCharset())


    fun process(text: List<String>, tolerance: Int) : Int {
        val init = text.mapIndexed { y, line -> line.mapIndexed { x, char -> Seat(x, y, char == 'L', false) } }
            .reduce { acc, list -> acc.union(list).toList() }

        var initial = WaitingArea(init.filter { it.isSeat })
        var next = initial.nextIter(initial::getNeighbours, tolerance)
        var loop = 2
        while (next.first) {
            println("Loop $loop")
            loop++
            next = next.second.nextIter(next.second::getNeighbours, tolerance )
        }
        return next.second.seats.count { it.isOccupied }
    }
    println(process(testDocs, 4))
//    println(process(puzzles))

    fun processP2(text: List<String>, tolerance: Int) : Int {
        val init = text.mapIndexed { y, line -> line.mapIndexed { x, char -> Seat(x, y, char == 'L', false) } }
            .reduce { acc, list -> acc.union(list).toList() }

        var initial = WaitingArea(init.filter { it.isSeat })
        var next = initial.nextIter(initial::getLOSNeighbours, tolerance)
        var loop = 2
        while (next.first) {
            println("Loop $loop")
            loop++
            next = next.second.nextIter(next.second::getLOSNeighbours, tolerance )
        }
        return next.second.seats.count { it.isOccupied }
    }

    println(processP2(testDocs, 5))
    println(processP2(puzzles, 5))



}

private fun printChart(mesg: String, initial: WaitingArea) {
    println(mesg)
    for (i in 0..9) {
        for (j in 0..9) {
            print(initial.seats.filter { it.x == j && it.y == i }.map {
                if (it.isSeat) {
                    if (it.isOccupied) {
                        "#"
                    } else {
                        "L"
                    }
                } else {
                    "."
                }
            }.first())
        }
        println()
    }
}


class WaitingArea(val seats: List<Seat>) {


    fun nextIter(f: (Seat) -> List<Seat>, tolerance: Int): Pair<Boolean, WaitingArea> {
        var changed = false
        var changedCOunt = 0
        val newSeats = seats.map { cellUnderRemapping ->

                val occupiedNeighbours = f(cellUnderRemapping).count { it.isOccupied }
                if (cellUnderRemapping.isOccupied && occupiedNeighbours >= tolerance) {
                    changed = true
                    changedCOunt++
                    Seat(cellUnderRemapping.x, cellUnderRemapping.y, true, false)
                } else if (!cellUnderRemapping.isOccupied && occupiedNeighbours == 0) {
                    changed = true
                    changedCOunt++
                    Seat(cellUnderRemapping.x, cellUnderRemapping.y, true, true)
                } else {
                    cellUnderRemapping
                }



        }

        println("$changed: $changedCOunt")

        return Pair(changed, WaitingArea(newSeats))
    }

    fun getNeighbours(seat: Seat): List<Seat> {
        return seats.filter{it.x != seat.x || it.y != seat.y}.filter{it.x in seat.x - 1..seat.x + 1 && it.y in seat.y - 1.. seat.y + 1 }
    }

    fun getLOSNeighbours(seat: Seat): List<Seat> {
        val topSeat = seats.filter { it.x == seat.x && it.y < seat.y }.maxByOrNull { it.y }
        val right = seats.filter { it.x > seat.x && it.y == seat.y }.minByOrNull { it.x }
        val bottom = seats.filter { it.x == seat.x && it.y > seat.y }.minByOrNull { it.y }
        val left = seats.filter { it.x < seat.x && it.y == seat.y }.maxByOrNull { it.x }

        val topRight = seats.filter {it.x - seat.x == (seat.y-it.y) && it.y < seat.y}.maxByOrNull { it.y }
        val bottomRight = seats.filter {it.x - seat.x == (it.y-seat.y) && it.y > seat.y}.minByOrNull { it.y }
        val bottomLeft = seats.filter {it.x - seat.x == (seat.y-it.y) && it.y > seat.y}.minByOrNull { it.y }
        val topLeft = seats.filter {it.x - seat.x == (it.y-seat.y) && it.y < seat.y}.maxByOrNull { it.y }
        return listOfNotNull(topSeat, right, left, bottom, topRight, bottomRight, bottomLeft, topLeft)
    }

}

class Seat(val x: Int, val y: Int, val isSeat: Boolean, val isOccupied: Boolean) {
    override fun toString(): String {
        return "($x,$y): $isSeat $isOccupied"
    }
}