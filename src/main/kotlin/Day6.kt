import java.io.File
import java.nio.charset.Charset

fun main() {
    val testDocs = File("test/day6").readText(Charset.defaultCharset()).split("\n\n")
    val puzzleDocs = File("puzzles/day6").readText(Charset.defaultCharset()).split("\n\n")

    println(testDocs.map{ answerGroup -> answerGroup.split("\n").flatMap { it.toList() }.groupBy { it }.size}.sum())
    println(puzzleDocs.map{ answerGroup -> answerGroup.split("\n").flatMap { it.toList() }.groupBy { it }.size}.sum())

    println(testDocs.map{ answerGroup -> answerGroup.split("\n").map{it.toList()}.reduce { acc, list -> acc.intersect(list)
        .toList() }.size}.sum())
    println(puzzleDocs.map{ answerGroup -> answerGroup.split("\n").map{it.toList()}.reduce { acc, list -> acc.intersect(list)
        .toList() }.size}.sum())
}