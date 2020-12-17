import java.io.File
import java.nio.charset.Charset

fun main() {
    val testDocs = File("test/day16").readText(Charset.defaultCharset())
    val puzzles = File("puzzles/day16").readText(Charset.defaultCharset())

    fun part1(input: String) {
        val ranges = input.split("\n\n")[0].split("\n").flatMap { policyLine ->
            policyLine.drop(policyLine.indexOf(":") + 1).trim().split(" ").filter { it != "or" }.flatMap { range ->
                range.split("-").let { thisRange -> thisRange[0].toInt().rangeTo(thisRange[1].toInt()).toList() }
            }
        }.distinct()

        println(ranges)
        val tickets = input.split("\n\n")[2].split("\n").drop(1)
            .flatMap { ticketLine -> ticketLine.split(",").map { it.toInt() }.filterNot { ranges.contains(it) } }.sum()

        println(tickets)
    }
    part1(testDocs)
    part1(puzzles)

    fun part2(input: String) {
        val allRangesCombined = input.split("\n\n")[0].split("\n").flatMap { policyLine ->
            policyLine.drop(policyLine.indexOf(":") + 1).trim().split(" ").filter { it != "or" }.flatMap { range ->
                range.split("-").let { thisRange -> thisRange[0].toInt().rangeTo(thisRange[1].toInt()).toList() }
            }
        }.distinct()

        val validTickets = input.split("\n\n")[2].split("\n").drop(1)
            .map { ticketLine -> ticketLine.split(",").map { it.toInt() } }
            .filter { singleTicket -> singleTicket.all { allRangesCombined.contains(it) } }

        val rangesByCategory = input.split("\n\n")[0].split("\n").map { policyLine ->
            policyLine.take(policyLine.indexOf(":")).trim() to policyLine.drop(policyLine.indexOf(":") + 1).trim()
                .split(" ").filter { it != "or" }.flatMap { range ->
                range.split("-").let { thisRange -> thisRange[0].toInt().rangeTo(thisRange[1].toInt()).toList() }
            }
        }.toMap()

        // the most obvious way to handle this is to search for elements with a single member, and add those to a mutable map, and then remove those elements from the next search and iterate until the map is full
        var fieldGroupedByRowId =
            validTickets.flatMap { it.mapIndexed { index, i -> Pair(index, i) } }.groupBy({ it.first }, { it.second })
                .mapValues { valuesForField ->
                    rangesByCategory.filter { catergoryRange ->
                        catergoryRange.value.containsAll(valuesForField.value)
                    }.map { it.key }.toMutableList()
                }.toMutableMap()

        fieldGroupedByRowId.forEach{
            println(it)
        }
        var listOfFields = mutableMapOf<Int, String>()

        while (fieldGroupedByRowId.isNotEmpty()) {
            val singlefields = fieldGroupedByRowId.filter { it.value.size == 1 }.mapValues { it.value.first() }
            listOfFields.putAll(singlefields)
            singlefields.forEach {
                fieldGroupedByRowId.remove(it.key)
            }

            fieldGroupedByRowId.forEach {
                it.value.removeAll(singlefields.values)
            }
        }

        println("List of fields: $listOfFields")

        val departureIndexes = listOfFields.filter { it.value.contains("departure") }
        println(departureIndexes)

        val myTicket = input.split("\n\n")[1].split("\n").drop(1).first().split(",")
            .filterIndexed { index, s -> departureIndexes.containsKey(index) }.map { it.toLong() }
            .reduceOrNull { acc, s -> acc * s }
        println(myTicket)
    }

    part2(testDocs)
    part2(
        "class: 0-1 or 4-19\n" +
                "row: 0-5 or 8-19\n" +
                "seat: 0-13 or 16-19\n" +
                "\n" +
                "your ticket:\n" +
                "11,12,13\n" +
                "\n" +
                "nearby tickets:\n" +
                "3,9,18\n" +
                "15,1,5\n" +
                "5,14,9"
    )

    part2(puzzles)
}


