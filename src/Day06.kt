fun main() {

    fun List<String>.toGrid(): Array<CharArray> {
        return Array(size) { i ->
            val line = get(i)
            CharArray(this[i].length) { j ->
                line[j]
            }
        }
    }

    fun Array<CharArray>.startingPosition(): RowCol {
        for (r in this.indices) {
            for (c in this[r].indices) {
                if (this[r][c] == '^') {
                    return RowCol(r, c)
                }
            }
        }
        error("Expected to find starting position")
    }

    fun Char.toDirection(): Direction = when (this) {
        '^' -> Direction.NORTH
        '>' -> Direction.EAST
        'v' -> Direction.SOUTH
        '<' -> Direction.WEST
        else -> error("Expected one of ^>v<")
    }

    fun RowCol.isInBounds(grid: Array<CharArray>): Boolean {
        return r >= 0 && r < grid.size && c >= 0 && c < grid[0].size
    }

    fun visit(startingPosition: RowCol, grid: Array<CharArray>): Set<RowCol> {
        var position = startingPosition
        val visitedPositions = hashSetOf(position)
        val visitedPositionDirections = hashSetOf<RowColDirection>()

        var direction = grid[position.r][position.c].toDirection()
        while (position.isInBounds(grid)) {
            val nextPosition = RowCol(position.r + direction.rOffset, position.c + direction.cOffset)
            if (!nextPosition.isInBounds(grid)) {
                break
            }
            if (grid[nextPosition.r][nextPosition.c] != '#') {
                position = nextPosition
                visitedPositions.add(position)
                if (!visitedPositionDirections.add(RowColDirection(position, direction))) {
                    return emptySet()
                }
            } else {
                direction = direction.next()
            }
        }
        return visitedPositions
    }

    fun part1(input: List<String>): Int {
        val grid = input.toGrid()
        val startingPosition = grid.startingPosition()
        val visitedPositions = visit(startingPosition, grid)
        return visitedPositions.size
    }

    fun part2(input: List<String>): Int {
        val grid = input.toGrid()
        val startingPosition = grid.startingPosition()
        val visitedPositions = visit(startingPosition, grid).toMutableSet()
        visitedPositions.remove(startingPosition)

        var count = 0
        for (pos in visitedPositions) {
            grid[pos.r][pos.c] = '#'
            if (visit(startingPosition, grid).isEmpty()) {
                count++
            }
            grid[pos.r][pos.c] = '.'
        }
        return count
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

data class RowCol(
    val r: Int,
    val c: Int,
)


enum class Direction(val rOffset: Int, val cOffset: Int) {
    NORTH(-1, 0),
    SOUTH(1, 0),
    EAST(0, 1),
    WEST(0, -1);

    fun next(): Direction = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }
}

data class RowColDirection(
    val rowCol: RowCol,
    val direction: Direction
)