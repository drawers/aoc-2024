import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {

    fun List<Int>.isSafe(): Boolean {
        var last: Int? = null
        var direction: Int? = null
        for (e in this) {
            if (last != null) {
                val diff = (e - last).absoluteValue
                if (diff > 3) return false

                val thisDirection = (e - last).sign
                if (direction != null && thisDirection != direction) {
                    return false
                }
                direction = thisDirection
            }
            last = e
        }
        return true
    }

    fun List<Int>.withIndexRemoved(index: Int): List<Int> = mapIndexedNotNull { i, e ->
        if (i == index) null else e
    }

    fun List<Int>.removalCombinations(): List<List<Int>> {
        return indices.map { this.withIndexRemoved(it) }
    }

    fun part1(input: List<String>): Int {
        return input.count {
            it.split(" ").map(String::toInt).isSafe()
        }
    }

    fun part2(input: List<String>): Int {
        return input.count {
            it.split(" ")
                .map(String::toInt)
                .removalCombinations()
                .any { list -> list.isSafe() }
        }
    }

    val input = readInput("Day02")
    part1(input).println() // 585
    part2(input).println() // 626
}
