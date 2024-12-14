fun main() {

    fun String.toDisk(): List<String> {
        var currentFileId = 0
        val disk = mutableListOf<String>()
        var isSpace = false
        for (c in this) {
            if (isSpace) {
                repeat(c.digitToInt()) {
                    disk.add(".")
                }
                isSpace = false
            } else {
                repeat(c.digitToInt()) {
                    disk.add(currentFileId.toString())
                    isSpace = true
                }
                currentFileId++
            }
        }
        return disk
    }

    fun MutableList<String>.swap(x: Int, y: Int) {
        val tmp = this[x]
        this[x] = this[y]
        this[y] = tmp
    }

    fun List<String>.blockDefragmented(): List<String> {
        var readCursor = lastIndex
        var writeCursor = 0

        val list = this.toMutableList()

        while (writeCursor < readCursor) {
            val writeValue = list.get(writeCursor)
            if (writeValue == ".") {
                val readValue = list.get(readCursor)
                if (readValue != ".") {
                    list.swap(readCursor, writeCursor)
                    writeCursor++
                    readCursor--
                } else {
                    readCursor--
                }
            } else {
                writeCursor++
            }
        }
        return list
    }

    fun List<String>.checkSum(): Long = foldIndexed(0L) { index, acc, s ->
        acc + if (s == ".") 0L else (index * s.toLong())
    }


    fun part1(input: List<String>): Long =
        input.first().toDisk().blockDefragmented().checkSum()


    fun part2(input: List<String>): Long = TODO()

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
