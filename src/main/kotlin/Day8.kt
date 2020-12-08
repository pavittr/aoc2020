import java.io.File
import java.nio.charset.Charset

fun main() {



    fun getAcc(inputDocs: List<String>): Pair<Int, Boolean> {
        val steps = inputDocs.mapIndexed { index, s -> Triple(index, s.split(" ")[0], s.split(" ")[1].toInt())  }

        val seen = mutableListOf<Triple<Int, String,Int>>()
        var step = steps[0]
        var acc = 0
        while (!seen.contains(step)) {
            seen.add(step)
            if (step.second == "jmp") {
                val nextStep = step.first + step.third
                if (nextStep < steps.size) {
                    step = steps[nextStep]
                } else {
                    return Pair(acc, false)
                }

            } else {
                if (step.second == "acc") {
                    acc +=  step.third
                }

                val nextStep = step.first + 1
                if (nextStep < steps.size) {
                    step = steps[nextStep]
                } else {
                    return Pair(acc, false)
                }

            }
        }
        return Pair(acc, true)

    }
    val testDocs = File("test/day8").readLines(Charset.defaultCharset())
    val puzzles = File("puzzles/day8").readLines(Charset.defaultCharset())
    println(getAcc(testDocs))
    println(getAcc(puzzles))


    for (i in 0..testDocs.size) {
        val spareDocs = testDocs.mapIndexed { index, s -> var returnS = s ; if (index == i) {if (s.startsWith("jmp")) {returnS = s.replace("jmp", "nop")} else { returnS = s.replace("nop", "jmp") }  } else {} ; returnS }
        val ans = getAcc(spareDocs)
        if (!ans.second) {
            println("Changing $i : ${ans.first}")
        }

    }

    for (i in 0..puzzles.size) {
        val spareDocs = puzzles.mapIndexed { index, s -> var returnS = s ; if (index == i) {if (s.startsWith("jmp")) {returnS = s.replace("jmp", "nop")} else { returnS = s.replace("nop", "jmp") }  } else {} ; returnS }
        val ans = getAcc(spareDocs)
        if (!ans.second) {
            println("Changing $i : ${ans.first}")
        }

    }



}



