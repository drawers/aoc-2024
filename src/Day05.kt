fun main() {

    val adj = hashMapOf<Int, MutableSet<Int>>()
    val incorrectlyOrdered = mutableListOf<List<Int>>()

    fun List<Int>.isValid(): Boolean {
        var prev = last()
        for (i in lastIndex - 1 downTo 0) {
            val curr = this[i]
            if (curr in adj[prev].orEmpty()) {
                return false
            }
            prev = curr
        }
        return true
    }

    fun part1(input: List<String>): Int {
        input.takeWhile {
            it.contains('|')
        }.map {
            it.split('|').map { s -> s.toInt() }
        }.forEach {
            adj.getOrPut(it[0]) { mutableSetOf(it[1]) }.add(it[1])
        }

        val paths = input.drop(input.takeWhile {
            it.contains('|')
        }.size + 1).map {
            it.split(',').map { s -> s.toInt() }
        }
        var sum = 0
        for (path in paths) {
            if (path.isValid()) {
                sum += path[path.size / 2]
            } else {
                incorrectlyOrdered.add(path)
            }
        }

        return sum
    }

    fun part2(): Int {
        var sum = 0
        val comparator = Comparator<Int> { o1, o2 -> if (adj[o1]?.contains(o2) == true) 1 else -1 }
        for (incorrect in incorrectlyOrdered) {
            val sorted = incorrect.sortedWith(comparator)
            sum += sorted[sorted.size / 2]
        }
        return sum
    }

    val input = readInput("Day05")
    part1(input).println()
    part2().println()
}
