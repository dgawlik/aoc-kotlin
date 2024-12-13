import kotlin.math.min


data class Point(val y: Int, val x: Int){
    operator fun plus(other: Point) = Point(y + other.y, x + other.x)

    fun gridContent(grid: List<List<Char>>): Char = grid.getOrNull(y)?.getOrNull(x) ?: '?'

    fun next(direction: Direction) = Point(y + direction.dy, x + direction.dx)
}

enum class Direction(val dy: Int, val dx: Int){
    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1);

    fun toPoint() = Point(dy, dx)

    fun next() = entries[(ordinal + 1) % entries.size]
}

class Guard(var visited: MutableSet<Pair<Point, Direction>>, done: Boolean, val lab: List<List<Char>>) {

    lateinit var currentPos: Pair<Point, Direction>

    var isLoop = false

    constructor(start: Pair<Point, Direction>, lab: List<List<Char>>) : this(mutableSetOf(start), false, lab) {
        this.currentPos = start
    }

    fun isOutside(pos: Point): Boolean {
        return pos.y < 0 || pos.y >= lab.size || pos.x < 0 || pos.x >= lab[0].size
    }

    fun isObstacle(pos: Point): Boolean {
        return pos.gridContent(lab) == '#'
    }

    fun walk(): Boolean {

        var exit = false
        var pos = currentPos

        if (isLoop) {
            return true
        }

        while (true) {
            val next = pos.first.next(pos.second)

            if (isOutside(next) || isObstacle(next)) {
                if (isOutside(next)) {
                    exit = true
                }
                break
            }


            visited.add(next to pos.second)
            pos = next to pos.second
        }

        currentPos = pos

        return exit
    }

    fun turn() {
        currentPos = currentPos.copy(second = currentPos.second.next())

        if (visited.contains(currentPos)) {
            isLoop = true
        }

        visited.add(currentPos)
    }
}


fun main() {


    fun part1(input: List<String>): Int {
        val lab = List(input.size) { input[it].toList() }
        val (n, m) = lab.size to lab[0].size

        val ind = (0..<n * m).find { lab[it / m][it % m] == '^' }

        val start = Point(ind!! / m , ind % m)

        val guard = Guard(start to Direction.UP, lab)

        while (!guard.walk()) {
            guard.turn()
        }

        return guard.visited.distinctBy { it.first }.size
    }


    fun part2(input: List<String>): Int {
        val lab = MutableList(input.size) { input[it].toMutableList() }
        val (n, m) = lab.size to lab[0].size

        val ind = (0..<n * m).find { lab[it / m][it % m] == '^' }

        val start = Point(ind!! / m , ind % m)

        val guard = Guard(start to Direction.UP, lab)

        while (!guard.walk()) {
            guard.turn()
        }

        val visited =  guard.visited

        var loopPoints = mutableSetOf<Point>()

        for ((point, direction) in visited){
            val next = point.next(direction)

            if (next.gridContent(lab) == '^' || next.gridContent(lab) == '?' || next.gridContent(lab) == '#') {
                continue
            }

            val prevField = lab[next.y][next.x]
            lab[next.y][next.x] = '#'

            val guard = Guard(start to Direction.UP, lab)

            while (!guard.walk()) {
                guard.turn()
            }

            if(guard.isLoop){
                loopPoints.add(next)
            }

            lab[next.y][next.x] =  prevField
        }

        return loopPoints.size
    }


    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
