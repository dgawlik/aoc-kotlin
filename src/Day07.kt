
fun main() {


    fun part1(input: List<String>): Long {

        fun allEvaluations(factors: List<Long>): List<Long> {
            if (factors.size == 1){
                return mutableListOf(factors[0])
            }

           return buildList<Long> {
                addAll(allEvaluations(factors.drop(1)).map { it+factors[0] })
                addAll(allEvaluations(factors.drop(1)).map { it*factors[0] })
            }
        }

        var sum = 0L

        for (i in input) {
            val (res, f) = i.split(":")

            var result = res.trim().toLong()
            var factors = f.trim().split(" ").map { it.toLong() }.reversed()

            if (allEvaluations(factors).contains(result)){
                sum += result
            }
        }

        return sum
    }


    fun part2(input: List<String>): Long {
        fun allEvaluations(lazyEvals: MutableList<Long>, factors: List<Long>): List<Long> {
            if (factors.size == 0){
                return lazyEvals
            }

            val newEvals = if (lazyEvals.isEmpty()) {
                mutableListOf(factors[0])
            } else {
                buildList {
                    addAll(lazyEvals.map { it+factors[0] })
                    addAll(lazyEvals.map { it*factors[0] })
                    addAll(lazyEvals.map { "$it${factors[0]}".toLong() })
                }.toMutableList()
            }

            return allEvaluations(newEvals, factors.drop(1))
        }

        var sum = 0L

        for (i in input) {
            val (res, f) = i.split(":")

            var result = res.trim().toLong()
            var factors = f.trim().split(" ").map { it.toLong() }

            if (allEvaluations(mutableListOf<Long>(), factors).contains(result)){
                sum += result
            }
        }

        return sum
    }


    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
