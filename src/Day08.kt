fun main() {

    fun List<String>.toGrid(): Array<CharArray> {
        return Array(size) { i ->
            val line = get(i)
            CharArray(this[i].length) { j ->
                line[j]
            }
        }
    }

    fun Array<CharArray>.toFrequencyMap(): Map<Char, List<RowCol>> {
        val frequencyToRowCols = hashMapOf<Char, MutableList<RowCol>>()
        for (r in indices) {
            for (c in this[0].indices) {
                if (this[r][c] == '.') continue

                val set = frequencyToRowCols.getOrPut(this[r][c]) { mutableListOf() }
                set.add(RowCol(r, c))
            }
        }
        return frequencyToRowCols
    }

    fun Pair<RowCol, RowCol>.directionalOffset(): Pair<Int, Int> {
        val rOffset = second.r - first.r
        val cOffset = second.c - first.c
        return Pair(rOffset, cOffset)
    }

    fun Pair<RowCol, RowCol>.antinode(): RowCol {
        val (rOffset, cOffset) = directionalOffset()

        val antinodeR = second.r + rOffset
        val antinodeC = second.c + cOffset
        return RowCol(antinodeR, antinodeC)
    }

    fun Pair<RowCol, RowCol>.harmonicAntinodes(n: Int, m: Int): List<RowCol> {
        val (rOffset, cOffset) = directionalOffset()

        var antinodeR = second.r + rOffset
        var antinodeC = second.c + cOffset

        val antinodes = mutableListOf<RowCol>()
        antinodes.add(second)
        while (antinodeR >= 0 && antinodeC >= 0 && antinodeR < n && antinodeC < m) {
            antinodes.add(RowCol(antinodeR, antinodeC))
            antinodeR += rOffset
            antinodeC += cOffset
        }

        return antinodes
    }

    fun <T> List<T>.pairs(): List<Pair<T, T>> {
        val pairs = mutableListOf<Pair<T, T>>()
        for (i in indices) {
            for (j in indices) {
                if (i == j) continue
                pairs.add(this[i] to this[j])
            }
        }
        return pairs
    }

    fun RowCol.isInBounds(n: Int, m: Int): Boolean {
        return r in 0..<n && c in 0..<m
    }

    fun part1(input: List<String>): Int {
        val grid = input.toGrid()
        val n = grid.size
        val m = grid[0].size

        return grid.toFrequencyMap().values.fold(setOf<RowCol>()) { acc, rowCols ->
            acc + rowCols.pairs().map { it.antinode() }.filter { it.isInBounds(n, m) }
        }.size
    }

    fun part2(input: List<String>): Int {
        val grid = input.toGrid()
        val n = grid.size
        val m = grid[0].size

        return grid.toFrequencyMap().values.fold(setOf<RowCol>()) { acc, rowCols ->
            acc + rowCols.pairs().map { it.harmonicAntinodes(n, m) }.flatten()
        }.size
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
