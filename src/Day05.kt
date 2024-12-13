import kotlin.math.min


class Day5Comparator(private val afters: Map<String, Set<String>>) : Comparator<String> {

    constructor(ordering: List<Pair<String, String>>) : this(buildMap<String, MutableSet<String>> {

        ordering.forEach { (x, y) -> getOrPut(x) { mutableSetOf() }.add(y) }

    })

    override fun compare(x: String?, y: String?): Int = when {

        y in afters[x].orEmpty() -> -1

        x in afters[y].orEmpty() -> 1

        else -> 0

    }

}

fun main() {

    fun part1(input: List<String>): Int {
        val rules = input.takeWhile { !it.isEmpty() }.map { val (l,r) = it.split("|"); l to r}.toList()
        val pageLists = input.dropWhile { !it.isEmpty() }.drop(1).map { it.split(",") }.toList()

        val comp = Day5Comparator(rules)
        val validLists = pageLists.filter { it == it.sortedWith(comp) }

        var sum = 0
        for (v in validLists){
            sum += v[v.size/2].toInt()
        }


        return sum
    }


    fun part2(input: List<String>): Int {
        val rules = input.takeWhile { !it.isEmpty() }.map { val (l,r) = it.split("|"); l to r}.toList()
        val pageLists = input.dropWhile { !it.isEmpty() }.drop(1).map { it.split(",") }.toList()

        val comp = Day5Comparator(rules)
        val invalidLists = pageLists.filter { it != it.sortedWith(comp) }

        var sum = 0
        for (v in invalidLists){
            val sv = v.sortedWith(comp)
            sum += sv[sv.size/2].toInt()
        }


        return sum
    }


    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
