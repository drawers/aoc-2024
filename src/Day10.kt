fun main() {

    fun List<String>.toGrid(): Array<CharArray> {
        return Array(size) { i ->
            val line = get(i)
            CharArray(this[i].length) { j ->
                line[j]
            }
        }
    }

    fun Array<CharArray>.trailheads(): List<RowCol> {
        val trailheads = mutableListOf<RowCol>()
        for (r in indices) {
            for (c in this[0].indices) {
                if (this[r][c] == '0') {
                    trailheads.add(RowCol(r, c))
                }
            }
        }
        return trailheads
    }

    fun RowCol.getNeighbors(
        grid: Array<CharArray>,
        path: Set<RowCol>,
        currentChar: Char
    ): MutableList<RowCol> {
        val neighbors = mutableListOf<RowCol>()
        for (direction in Direction.entries) {
            val newR = r + direction.rOffset
            val newC = c + direction.cOffset
            if (newR < 0 || newR > grid.lastIndex || newC < 0 || newC > grid[0].lastIndex) continue
            val newRowCol = RowCol(newR, newC)
            if (newRowCol in path) continue
            if ((grid[newRowCol.r][newRowCol.c].digitToIntOrNull() ?: -1) != currentChar.digitToInt() + 1) continue
            neighbors.add(newRowCol)
        }
        return neighbors
    }

    fun traverse(
        grid: Array<CharArray>,
        start: RowCol,
        current: RowCol,
        path: MutableSet<RowCol>,
        nines: MutableCollection<RowCol>
    ) {
        val currentChar = grid[current.r][current.c]

        if (currentChar == '9') {
            nines.add(current)
            return
        }

        val neighbors = current.getNeighbors(grid, path, currentChar)
        neighbors.forEach {
            path.add(it)
            traverse(grid, start, it, path, nines)
            path.remove(it)
        }
    }

    fun part1(input: List<String>): Int {
        val grid = input.toGrid()
        val trailheads = grid.trailheads()
        var score = 0
        for (head in trailheads) {
            val nines = mutableSetOf<RowCol>()
            traverse(grid, head, head, mutableSetOf(head), nines)
            score += nines.size
        }
        return score
    }


    fun part2(input: List<String>): Int {
        val grid = input.toGrid()
        val trailheads = grid.trailheads()
        var score = 0
        for (head in trailheads) {
            val nines = mutableListOf<RowCol>()
            traverse(grid, head, head, mutableSetOf(head), nines)
            score += nines.size
        }
        return score
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
