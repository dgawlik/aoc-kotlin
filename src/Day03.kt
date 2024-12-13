import kotlin.math.abs
import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        return input.fold(0, { acc, line ->
            val instruction = "mul\\((\\d{1,3}),(\\d{1,3})\\)".toRegex()

            val matches = instruction.findAll(line).toList()

            var sum = 0
            for (match in matches) {
                val (a, b) = match.destructured
                sum += a.toInt() * b.toInt()
            }

            acc + sum
        })
    }

    fun part2(input: List<String>): Int {
        val text =  input.joinToString("")

        val instruction = "mul\\((\\d{1,3}),(\\d{1,3})\\)".toRegex()
        val _do = "do\\(\\)".toRegex()
        val dont = "don't\\(\\)".toRegex()

        val matches = instruction.findAll(text).map { Triple(it.range.first, it, "mul") }.toMutableList()
        matches.addAll(_do.findAll(text).map { Triple(it.range.first, it, "do") })
        matches.addAll(dont.findAll(text).map { Triple(it.range.first, it, "dont") })
        matches.sortBy { it.first }

        var sum = 0
        var isOn = true
        for ((_, match, op) in matches) {
            when (op) {
                "mul" -> {
                    if (isOn) {
                        val (a, b) = match.destructured
                        sum += a.toInt() * b.toInt()
                    }
                }

                "do" -> isOn = true
                "dont" -> isOn = false
            }
        }

        return sum
    }

//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
