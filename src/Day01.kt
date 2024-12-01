import java.util.*
import kotlin.math.absoluteValue

fun main() {

    fun part1(input: List<String>): Int {
        val left = PriorityQueue<Int>(input.size)
        val right = PriorityQueue<Int>(input.size)
        input.forEach { line ->
            val (l, r) = line.split("   ").map { it.toInt() }
            left.offer(l)
            right.offer(r)
        }
        var sum = 0
        while (left.isNotEmpty()) {
            val l = left.poll()
            val r = right.poll()
            sum += (r - l).absoluteValue
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val left = ArrayList<Int>(input.size)
        val right = ArrayList<Int>(input.size)
        input.forEach { line ->
            val (l, r) = line.split("   ").map { it.toInt() }
            left.add(l)
            right.add(r)
        }
        val rightValueToCount = right.groupingBy { it }.eachCount()
        return left.reduce { acc, i ->
            acc + rightValueToCount.getOrDefault(i, 0) * i
        }
    }

    val input = readInput("Day01")
    part1(input).println() // 1320851
    part2(input).println() // 26890776
}
