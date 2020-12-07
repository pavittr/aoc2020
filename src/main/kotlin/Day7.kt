import java.io.File
import java.nio.charset.Charset

fun main() {

    fun countOptions(policyLines: List<String>): Int {

        val policies = policyLines.associate { policy ->
            val separators = policy.split("bags contain"); Pair(
            separators[0].trim(),
            separators[1].dropLast(1).replace("bags", "").replace("bag", "").split(",")
                .map { containers -> containers.trim().split(" ").drop(1).filter { it != "other" }.joinToString(" ") })
        }


        fun findChildren(curreLevel: String): List<String> {
            val directChildren = policies.getOrDefault(curreLevel, listOf())
            val allChildren = directChildren.map { findChildren(it) }.flatMap { it }.filter { it.isNotEmpty() }
            return directChildren.union(allChildren).toList()
        }

        val policyParents = policies.keys

      return policyParents.map { findChildren(it) }.filter { it.contains("shiny gold") }.size
    }
    val testDocs = File("test/day7").readLines(Charset.defaultCharset())
    val puzzles = File("puzzles/day7").readLines(Charset.defaultCharset())

    println(countOptions(testDocs))
    println(countOptions(puzzles))

    fun countBags(policyLines: List<String>): Int {

        val policies = policyLines.associate { policy ->
            val separators = policy.split("bags contain"); Pair(
            separators[0].trim(),
            separators[1].dropLast(1).replace("bags", "").replace("bag", "").split(",")
                .map { containers -> containers.trim().let{val description = it.replace("no other", "0 adjectivised colour").split(" "); Pair(description[0].toInt(), description[1] + " " + description[2] )}})
        }
        fun findSubBags(entrybag: String): Int {
            val bagChildren = policies.getOrDefault(entrybag, listOf())
            return 1 + bagChildren.map { findSubBags(it.second) * it.first }.sum()
        }

        return findSubBags("shiny gold") -1

    }

    println(countBags(testDocs))
    println(countBags(puzzles))
}



