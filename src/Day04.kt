import kotlin.math.min


fun main() {

    fun part1(input: List<String>): Int {
        val matrix = Array(input.size) { CharArray(input.size) }

        val strings = mutableListOf<String>()

        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, c ->
                matrix[i][j] = c
            }
        }

        val n = matrix.size


        for (i in 0..<n){
            val text = (0..<n).map { matrix[i][it]}.joinToString("")
            strings.add(text)
        }

        for (i in 0..<n){
            val text = (0..<n).map { matrix[it][i]}.joinToString("")
            strings.add(text)
        }


        for (i in -n+1..n-1){
            val start = if (i < 0) -i to 0 else 0 to i

            val diag = generateSequence(start) { (y, x) ->
                if (y < n-1 && x < n-1) y+1 to x+1 else null
            }.map{matrix[it.first][it.second]}.joinToString("")

            strings.add(diag)
        }

        for (i in -n+1..n-1){
            val start = if (i < 0) -i to n-1 else 0 to n-i-1

            val diag = generateSequence(start) { (y, x) ->
                if (y < n-1 && x > 0) y+1 to x-1 else null
            }.map{matrix[it.first][it.second]}.joinToString("")

            strings.add(diag)
        }

        strings += strings.map {it.reversed()}


        return strings.fold (0, {acc, it ->
            acc+(it.windowed("XMAS".length).filter {s ->
                s == "XMAS"}.count())})
    }

    fun part2(input: List<String>): Int {
        val patt1 = "M.S.A.M.S".toRegex()
        val patt2 = "S.M.A.S.M".toRegex()
        val patt3 = "M.M.A.S.S".toRegex()
        val patt4 = "S.S.A.M.M".toRegex()

        val matrix = Array(input.size) { CharArray(input.size) }

        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, c ->
                matrix[i][j] = c
            }
        }

        val n = matrix.size

        var sum = 0
        for (i in 0..n-3){
            for(j in 0..n-3) {
                val text = (0..<9).map { matrix[i+it/3][j+it%3] }.joinToString("")

                if (patt1.matches(text) || patt2.matches(text) || patt3.matches(text) || patt4.matches(text)){
                    sum++
                }
            }
        }

        return sum
    }

//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
