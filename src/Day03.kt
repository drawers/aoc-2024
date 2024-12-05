fun main() {

    val numberChars = setOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    val mulChars = setOf('m', 'u', 'l')
    val separatorChars = setOf('(', ')', ',')
    val doChars = "do".toSet()
    val dontChars = "don't".toSet()
    val validChars = numberChars + separatorChars + mulChars + doChars + dontChars

    fun ArrayDeque<Char>.unwindOperand(terminator: Char): Int? {
        val numbers = ArrayDeque<Char>()
        var last = removeLastOrNull()
        while (last != null && last != terminator) {
            if (last in numberChars) {
                numbers.addFirst(last)
            } else {
                return null
            }
            last = removeLastOrNull()
        }
        return String(numbers.toCharArray()).toIntOrNull()
    }

    fun ArrayDeque<Char>.unwindDoOp(): Boolean? {
        val last = lastOrNull() ?: return null
        if (last == '(') {
            if (dropLast(1).takeLast(2) == listOf('d', 'o')) {
                clear()
                return true
            } else if (dropLast(1).takeLast(5) == listOf('d', 'o', 'n', '\'', 't')) {
                clear()
                return false
            } else {
                return null
            }
        }

        return null
    }

    fun ArrayDeque<Char>.unwindMul(): Unit? {
        val l = removeLastOrNull()
        val u = removeLastOrNull()
        val m = removeLastOrNull()
        return if (l == 'l' && u == 'u' && m == 'm') Unit else null
    }

    fun process(input: List<String>, processDoOps: Boolean): Int {
        val stack = ArrayDeque<Char>()
        val results = mutableListOf<Int>()
        var enabled = true
        for (line in input) {
            for (c in line) {
                if (c !in validChars) {
                    stack.clear()
                    continue
                }
                if (c == ')') {
                    if (processDoOps) {
                        val doOp = stack.unwindDoOp()
                        if (doOp == true) {
                            enabled = true
                        } else if (doOp == false) {
                            enabled = false
                        }
                    }

                    val op1 = stack.unwindOperand(',') ?: continue
                    val op2 = stack.unwindOperand('(') ?: continue
                    stack.unwindMul() ?: continue
                    if (enabled) {
                        results.add(op1 * op2)
                    }
                } else {
                    stack.add(c)
                }
            }
        }
        return results.sum()
    }

    fun part1(input: List<String>): Int {
        return process(input, false)
    }


    fun part2(input: List<String>): Int {
        return process(input, true)
    }

    val input = readInput("Day03")
    part1(input).println() // 188116424
    part2(input).println() // 104245808
}
