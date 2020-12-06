import java.io.File
import java.nio.charset.Charset

fun main() {
    println(File("test/day6").readText(Charset.defaultCharset()).split("\n\n").map{ answerGroup -> answerGroup.split("\n").flatMap { it.toList() }.groupBy { it }.size}.sum())
    println(File("puzzles/day6").readText(Charset.defaultCharset()).split("\n\n").map{ answerGroup -> answerGroup.split("\n").flatMap { it.toList() }.groupBy { it }.size}.sum())


    println(File("test/day6").readText(Charset.defaultCharset()).split("\n\n").map{ answerGroup -> answerGroup.split("\n").map{it.toList()}.reduce { acc, list -> acc.intersect(list)
        .toList() }.size}.sum())

    println(File("puzzles/day6").readText(Charset.defaultCharset()).split("\n\n").map{ answerGroup -> answerGroup.split("\n").map{it.toList()}.reduce { acc, list -> acc.intersect(list)
        .toList() }.size}.sum())
}