import java.io.File
import java.nio.charset.Charset

class Day2 {
    fun makeItSo() {
        println(File("test/day2").readLines(Charset.defaultCharset()).map{ Password(it)}.filter { it.isSledValidValid() }.count())
        println(File("puzzles/day2").readLines(Charset.defaultCharset()).map{ Password(it)}.filter { it.isSledValidValid() }.count())

        println(File("test/day2").readLines(Charset.defaultCharset()).map{ Password(it)}.filter { it.isTobogganValid() }.count())
        println(File("puzzles/day2").readLines(Charset.defaultCharset()).map{ Password(it)}.filter { it.isTobogganValid() }.count())
    }
}

class Password(policyLine: String) {
    private val bits = policyLine.split("-", " ", ":")
    private val lower = bits[0].toInt()
    private val upper = bits[1].toInt()
    private val policyCher = bits[2].toCharArray()[0]
    private val password = bits[4]

    fun isSledValidValid() : Boolean {
        val letterCount = password.count{c ->  c == policyCher}
        return letterCount in lower..upper
    }

    fun isTobogganValid() : Boolean {
        return (password[lower-1] == policyCher && password[upper-1] != policyCher) || (password[lower-1] != policyCher && password[upper-1] == policyCher)
    }
}
