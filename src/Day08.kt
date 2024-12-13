
fun main() {

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
        operator fun minus(other: Point) = Point(x - other.x, y - other.y)

        fun id(city: List<List<Char>>)
            = if (city[y][x].isDigit() || city[y][x].isLetter())
                    city[y][x]
              else '?'

        fun insideCity(city: List<List<Char>>) = x in 0 until city[0].size && y in 0 until city.size

        fun antinodes(other: Point, city: List<List<Char>>): List<Point> {
            val left = other + (other - this)
            val right = this + (this - other)

            var result = mutableListOf<Point>()
            if (left.insideCity(city))
                result += left
            if (right.insideCity(city))
                result += right

            return result
        }

        fun antinodes2(other: Point, city: List<List<Char>>): List<Point> {
            val t = this
            val left = (other - this)
            val right = (this - other)

            return buildList {
                add(other)
                addAll( generateSequence(other) {
                    if ((it + left).insideCity(city)) {
                        it + left
                    } else {
                        null
                    }
                })
                addAll( generateSequence(t) {
                    if ((it + right).insideCity(city)) {
                        it + right
                    } else {
                        null
                    }
                })
            }
        }
    }


    fun part1(input: List<String>): Int {
        val city = input.map { it.toList() }
        val (n, m) = city.size to city[0].size

        val frequencies = (0..<n*m)
            .map {Point(it%m,it/m)}
            .filter { it.id(city) != '?' }
            .groupBy { it.id(city)}

        var result = mutableSetOf<Point>()
        for ((k, antennas) in frequencies) {
            for (i in 0..<antennas.size-1) {
                for (j in i+1..antennas.size-1) {
                    result += antennas[i].antinodes(antennas[j], city)
                }
            }
        }

        return result.size
    }


    fun part2(input: List<String>): Int {
        val city = input.map { it.toList() }
        val (n, m) = city.size to city[0].size

        val frequencies = (0..<n*m)
            .map {Point(it%m,it/m)}
            .filter { it.id(city) != '?' }
            .groupBy { it.id(city)}

        var result = mutableSetOf<Point>()
        for ((k, antennas) in frequencies) {
            for (i in 0..<antennas.size-1) {
                for (j in i+1..antennas.size-1) {
                    result += antennas[i].antinodes2(antennas[j], city)
                }
            }
        }

        return result.size
    }


    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
