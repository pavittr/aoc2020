import java.io.File
import java.nio.charset.Charset
import kotlin.concurrent.thread
import kotlin.math.absoluteValue
import kotlin.streams.toList

fun main() {
    val testDocs = File("test/day13").readLines(Charset.defaultCharset())
    val puzzles = File("puzzles/day13").readLines(Charset.defaultCharset())


    fun part1(input : List<String>) {
        val minTime = input[0].toInt()
        val schedules = input[1].split(",").filter { it != "x" }.map{it.toInt()}
        println(schedules)

        println(schedules.map { Pair(it - (minTime % it), it) }.minByOrNull { it.first }?.let{it.first * it?.second })

    }

    part1(testDocs)
    part1(puzzles)

    fun part2(input: List<String>, start: Long) {
        val times = input[1].split(",").mapIndexed{index, id -> Pair(index, id) }.filter { it.second != "x" }.map { Pair(it.first.toLong(), it.second.toLong()) }
        println(times)

        // 7x                      + 0 = q
        //     13y                 - 1 = q
        //         59z             - 4 = q
        //               31w       - 6 = q
        //                     19v - 7 = q



        // The earliest timestamp that matches the list 17,x,13,19 is 3417.

        // 67x , 7y - 1, 59z - 2, 61w - 3 = 754018.
        // 67x , 7y - 2  59z - 3, 61w - 4 = 779210.
        // 67,7,x,59,61 first occurs at timestamp 1261476.
        //1789,37,47,1889 first occurs at timestamp 1202161486.

        val highest = times.maxByOrNull { it.second } ?: Pair(0L, 0L)

        val offsets = times.map{Pair(it.first - highest.first, it.second)}

        var captured = 0L
        val period = times.map{it.second}.reduce { acc, l -> acc.times(l) }
        val increase =  19L.times(521L).times(37L).times(17L)
        var i = start.minus(19L)
        println("Period is $period, increase is $increase")
//        while (i <= period.times(2L)) {
        while (i <= period*2L) {
            i = i.plus(increase)
           //println("Testing $i")
            var found = true
            for (pair in times) {
                if ((i.plus(pair.first).rem(pair.second)) != 0L) {
                    found = false
                    break
                }
            }
            if (found) {
                captured = i
                break
            }
        }

        println(captured)

// 100048220210295

        // 1068781   1068782
        // 7              13
        // 152683      82214

        // 1068781 =      7                             * 61              * 2503
        // 1068782 = 2      * 11 * 13              * 37      * 101
        // 1068785 =      5              * 59                                    * 3623
        // 1068787 =                  23      * 31                 * 1499
        // 1068788 =  2 * 2 * 7 * 7 * 7 * 19 * 41
        //


   //     println(times.map{it.first.plus(1L).times(it.second)}.reduce{acc, i -> acc.times(i)}.minus(highest.first))



    }

//    part2(testDocs, 0L)
//    part2(  listOf("","67,x,7,59,61"), 0L)
//
        part2(puzzles,  0L)
 //                          1157701781269163
//     Lower   part2(puzzles, 100000000000000L)
//     Upper    part2(puzzles, 500000000000000L)


}




