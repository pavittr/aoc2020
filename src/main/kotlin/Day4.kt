import java.io.File
import java.nio.charset.Charset

class Day4 {
    fun makeItSo() {
        val expectedKeys = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

        println(File("test/day4").readText(Charset.defaultCharset()).split("\n\n").map { docLine ->
            docLine.split(" ", "\n").associate { docElement -> docElement.split(":").let{ (prop, value) -> prop to value } }
        }.filter { it.keys.containsAll(expectedKeys) }.count())



        val realDocs = File("puzzles/day4").readText(Charset.defaultCharset()).split("\n\n").map { docLine ->
            docLine.split(" ", "\n").associate { docElement -> docElement.split(":").let{ (prop, value) -> prop to value } }
        }
        println(realDocs.filter { it.keys.containsAll(expectedKeys) }.count())

        val validators:List<(Map<String,String>)->Boolean> = listOf(
            {map -> map["byr"]?.toInt() ?: -1 in 1920..2002},
            {map -> map["iyr"]?.toInt() ?: -1 in 2010..2020},
            {map -> map["eyr"]?.toInt() ?: -1 in 2020..2030},
            {map ->
                val hgt = map.getOrDefault("hgt", "invalid").dropLast(2).toIntOrNull()
                when (map.getOrDefault("hgt", "invalid").takeLast(2)) {
                    "cm" -> hgt in 150..193
                    "in" -> hgt in 59..76
                    else -> false
                }
            },
            {map -> map["hcl"]?.matches(Regex("#[0-9a-f]{6}"))!! },
            {map -> listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(map["ecl"]) },
            {map -> map["pid"]?.matches(Regex("[0-9]{9}"))!! },
        )
        println(realDocs.filter { it.keys.containsAll(expectedKeys) }.map { validators.map { validator -> validator(it) }.count { !it }}.count{it == 0})
    }
}
