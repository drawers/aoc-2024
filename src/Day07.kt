fun main() {

    data class Equation(
        val testValue: Long, val numbers: List<Long>
    )

    fun String.toEquation(): Equation {
        val (head, tail) = split(":")
        return Equation(head.toLong(), tail.drop(1).split(" ").map(String::toLong))
    }

    fun List<String>.toEquations(): List<Equation> = map { it.toEquation() }

    fun isValid(equation: Equation, total: Long, index: Int, allowConcat: Boolean): Boolean {
        if (index == equation.numbers.lastIndex && total == equation.testValue) {
            return true
        }
        if (total > equation.testValue) {
            return false
        }
        if (index > equation.numbers.lastIndex - 1) {
            return false
        }

        val newTotalWithAddition = total + equation.numbers[index + 1]
        val newTotalWithMultiplication = total * equation.numbers[index + 1]

        val newTotalWithConcatenation = if (allowConcat) {
            (total.toString() + equation.numbers[index + 1].toString()).toLong()
        } else {
            -1L
        }

        return isValid(equation, newTotalWithAddition, index + 1, allowConcat) || isValid(
            equation, newTotalWithMultiplication, index + 1, allowConcat
        ) || if (allowConcat) {
            isValid(equation, newTotalWithConcatenation, index + 1, allowConcat)
        } else false
    }

    fun part1(input: List<String>): Long {
        return input.toEquations().filter { isValid(it, it.numbers.first(), 0, false) }.sumOf { it.testValue }
    }

    fun part2(input: List<String>): Long {
        return input.toEquations().filter { isValid(it, it.numbers.first(), 0, true) }.sumOf { it.testValue }
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
