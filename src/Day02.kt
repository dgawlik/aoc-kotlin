import kotlin.math.abs
import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        return input.fold(0, { acc, line ->
            val parts = line.split("\\s+".toRegex()).map {it.toInt()}

            val safe = (1..<parts.size).all {
                parts[it] - parts[it-1] in 1..3
            } || (1..<parts.size).all {
                parts[it-1] - parts[it] in 1..3
            }

            if (safe) acc + 1 else acc
        })
    }

    fun part2(input: List<String>): Int {



        return input.fold(0, { acc, line ->
            val parts = line.split("\\s+".toRegex()).map {it.toInt()}

            val isSafe = { sign: Int ->
                var safe = true
                var window = mutableListOf(parts[0], parts[1], parts[2])
                var dampen = true

                var ind = 2
                while (ind < parts.size) {
                    val (a, b, c) = window

                    if(b-a !in 1*sign..3*sign &&
                        c-b !in 1*sign..3*sign &&
                            c-a !in 1*sign..3*sign) {
                        safe = false
                        break
                    } else if (dampen && c-b !in 1*sign..3*sign
                        && b-a !in 1*sign..3*sign) {
                        dampen = false
                        window.removeAt(1)
                    } else if (dampen && b-a !in 1*sign..3*sign) {
                        window.removeAt(0)
                        dampen = false

                    } else if (dampen && c-b !in 1*sign..3*sign) {
                        window.removeAt(2)
                        dampen = false
                    } else if (b-a !in 1*sign..3*sign || c-b !in 1*sign..3*sign) {
                        safe = false
                        break
                    } else {
                        window.removeAt(0)
                    }

                    window.add(parts[ind++])
                }

                safe

            }

            if (isSafe(1) || isSafe(-1)) acc + 1 else acc
        })
    }

//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
