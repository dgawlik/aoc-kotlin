import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {

        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()
        input.forEach { line ->
            val parts = line.split("\\s+".toRegex())
            left.add(parts[0].toInt())
            right.add(parts[1].toInt())
        }

        left.sort()
        right.sort()

        var sumOfDifferences = 0
        left.zip(right).forEach { (l, r) ->
            sumOfDifferences += abs(r - l)
        }

        return sumOfDifferences
    }

    fun part2(input: List<String>): Int {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()
        input.forEach { line ->
            val parts = line.split("\\s+".toRegex())
            left.add(parts[0].toInt())
            right.add(parts[1].toInt())
        }

        val rightCounts = right.groupBy { it }.mapValues { it.value.count() }

        var sum = 0
        left.forEach({ l ->
            sum += rightCounts.getOrDefault(l, 0) * l
        })

        return sum
    }

//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
