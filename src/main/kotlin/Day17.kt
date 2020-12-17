import java.io.File
import java.nio.charset.Charset

fun main() {
    fun part1(inputFile: String) {
        val input = File(inputFile).readLines(Charset.defaultCharset())

        var elements = input.flatMapIndexed { y, line -> line.mapIndexed { x, c -> if (c == '#') {Pair(Triple(x,y,0), true) } else {Pair(Triple(x,y,0), false) }}}.filter{it.second}
        var nextElements = elements.toMutableList()
        println(elements)
        println(nextElements)


        for (i in 1..6) {
            val previousElements = nextElements.toMutableList()
            nextElements.clear()
            println ("Processing $i, $nextElements, $previousElements")
            val maxX = (previousElements.maxByOrNull { it.first.first }?.first?.first ?: 0) + 1
            val minX = (previousElements.minByOrNull { it.first.first }?.first?.first ?: 0) - 1
            val maxY = (previousElements.maxByOrNull { it.first.second }?.first?.second ?: 0) + 1
            val minY = (previousElements.minByOrNull { it.first.second }?.first?.second ?: 0) - 1
            val maxZ = (previousElements.maxByOrNull { it.first.third }?.first?.third ?: 0) + 1
            val minZ = (previousElements.minByOrNull { it.first.third }?.first?.third ?: 0) - 1
            println("$minX to $maxX, $minY to $maxY, $minZ to $maxZ")
            for (x in minX..maxX) {
                for (y in minY..maxY) {
                    for (z in minZ..maxZ) {
                        // look at the cell and get its list of neighbours

                        val activeNeighbours = previousElements.filter {
                            it.first.first in x-1..x+1 &&
                            it.first.second in y-1..y+1 &&
                            it.first.third in z-1..z+1 &&
                                    (it.first.first != x || it.first.second != y || it.first.third != z )
                        }

                        val thisCell = previousElements.filter { it.first.first == x && it.first.second == y && it.first.third == z  }.firstOrNull() ?: Pair(Triple(x,y,z), false)

                        if (thisCell.first.first == 0 && thisCell.first.second == 1  && thisCell.first.third == -1 ) {
                            println("Neighbours for $thisCell are $activeNeighbours")
                        }
                        if (thisCell.second && activeNeighbours.count() in 2..3) {


                            nextElements.add(Pair(thisCell.first, true))

                        } else if (!thisCell.second && activeNeighbours.count() == 3) {
                            nextElements.add(Pair(thisCell.first, true))
                        }

                    }
                }
            }
            println ("Ending $i, ${nextElements.sortedBy { it.first.third}}, $previousElements")
        }


        println(nextElements.count())
    }

    part1("test/day17")
    part1("puzzles/day17")


    fun part2(inputFile: String) {
        val input = File(inputFile).readLines(Charset.defaultCharset())

        var elements = input.flatMapIndexed { y, line -> line.mapIndexed { x, c -> if (c == '#') {Cube(0, x,y,0, true) } else {Cube(0, x,y,0, false) }}}.filter{it.active}
        var nextElements = elements.toMutableList()

        for (i in 1..6) {
            val previousElements = nextElements.toMutableList()
            nextElements.clear()
            val maxW = (previousElements.maxByOrNull { it.w }?.w ?: 0) + 1
            val minW = (previousElements.minByOrNull { it.w }?.w ?: 0) - 1
            val maxX = (previousElements.maxByOrNull { it.x }?.x ?: 0) + 1
            val minX = (previousElements.minByOrNull { it.x }?.x ?: 0) - 1
            val maxY = (previousElements.maxByOrNull { it.y }?.y ?: 0) + 1
            val minY = (previousElements.minByOrNull { it.y }?.y ?: 0) - 1
            val maxZ = (previousElements.maxByOrNull { it.z }?.z ?: 0) + 1
            val minZ = (previousElements.minByOrNull { it.z }?.z ?: 0) - 1
            for (w in minW..maxW) {
            for (x in minX..maxX) {
                for (y in minY..maxY) {
                    for (z in minZ..maxZ) {
                        // look at the cell and get its list of neighbours

                        val activeNeighbours = previousElements.filter {
                            it.w in w-1..w+1 &&
                            it.x in x-1..x+1 &&
                                    it.y in y-1..y+1 &&
                                    it.z in z-1..z+1 &&
                                    (it.w != w || it.x != x || it.y != y || it.z != z )
                        }

                        val thisCell = previousElements.firstOrNull { it.w == w && it.x == x && it.y == y && it.z == z } ?: Cube(w, x,y,z,false)

                        if (thisCell.active && activeNeighbours.count() in 2..3) {
                            nextElements.add(Cube(thisCell.w, thisCell.x, thisCell.y, thisCell.z, true))
                        } else if (!thisCell.active && activeNeighbours.count() == 3) {
                            nextElements.add(Cube(thisCell.w, thisCell.x, thisCell.y, thisCell.z, true))
                        }
                    }
                }
            }}
        }

        println(nextElements.count())
    }

    part2("test/day17")
    part2("puzzles/day17")

}

class Cube(val w:Int, val x:Int, val y:Int, val z:Int, val active: Boolean){}
