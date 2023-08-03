package tictactoe

/**
 * Represents a Tic-Tac-Toe game table with the logic to play the game.
 */
class GameTable {
    /**
     * The current state of the game.
     */
    val dataGameState = GameState.initialState
    val gameAnalyzer = GameAnalyzer(dataGameState)

    /**
     * Initializes the game table and displays it.
     */
    init {
        buildTableState()
        displayTable()
    }

/**
 * Executes the game, allowing user interactions to make moves.
 *
 * This property is responsible for running the game loop and handling user interactions.
 * It repeatedly calls the [userInteraction] function to get the user's desired move in the form of coordinates.
 * The game loop continues until the user provides valid coordinates.
 * Once valid coordinates are obtained, the [updateTableState] function is called to update the game state based on the move.
 * The [displayTable] function is then invoked to show the updated game table.
 * Finally, the [analyzeGame] function from the [gameAnalyzer] object is called to perform any necessary analysis on the current game state.
 *
 * */
    val executeGame: Unit
        get() {
        var coordinates = listOf(0)
        while (coordinates[0] == 0) { coordinates = userInteraction() }
        if (coordinates[0] > 0) {
            updateTableState(coordinates)
            displayTable()
            gameAnalyzer.analyzeGame
        }
    }

    /**
     * A function to handle user input and return the chosen coordinates for the next move.
     *
     * @return A list containing the row and column coordinates (1-indexed) of the next move.
     *         If an invalid input is provided, a default list of [0] is returned.
     */
    private fun userInteraction(): List<Int> {
        val input = readln().split(" ")
        val coordinatesList = input.mapNotNull { it.toIntOrNull() }

        return if (input.any { !it.isInt() }) {
            println("You should enter numbers!")
            listOf(0)
        } else {
            when {
                coordinatesList.size != 2 -> {
                    println("You should enter two numbers separated by a space!")
                    listOf(0)
                }

                coordinatesList[0] !in 1..3 || coordinatesList[1] !in 1..3 -> {
                    println("Coordinates should be from 1 to 3!")
                    listOf(0)
                }

                isCellOccupied(coordinatesList) -> {
                    println("This cell is occupied! Choose another one!")
                    listOf(0)
                }

                else -> coordinatesList
            }
        }
    }

    /**
     * Checks if a given string can be parsed to an integer.
     *
     * @return `true` if the string is a valid integer, `false` otherwise.
     */
    private fun String.isInt(): Boolean {
        return try {
            toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }


    private fun isCellOccupied(coordinates: List<Int>): Boolean {
        val row = coordinates[0] - 1
        val column = coordinates[1] - 1

        return dataGameState.valuesTable[row][column] != ' '
    }

    private fun updateTableState(coordinates: List<Int>) {
        val row = coordinates[0] - 1
        val column = coordinates[1] - 1

        if (dataGameState.currentPlayer == 'X') {
            dataGameState.currentPlayer = 'O'
        } else {
            dataGameState.currentPlayer = 'X'
        }


        dataGameState.valuesTable[row][column] = dataGameState.currentPlayer
    }

    /**
     * Populates the dataGameState's valuesTable by taking user input as a string and arranging
     * the characters in a 3x3 table.
     *
     * The user is prompted to enter a string, and this string is then split into characters and
     * added to the dataGameState's valuesTable following the 3x3 table arrangement.
     */
    private fun buildTableState() {
        val initialState = "         " // Nine empty spaces.

        for (i in 0 until MAX_INDEX) {
            for (j in 0 until MAX_INDEX) {
                /**Multiply [i] by [MAX_COLUMN] to get the starting index of the row, and then add j to get the final index within that row*/
                val index = i * MAX_COLUMN + j
                dataGameState.valuesTable[i].add(initialState[index])

            }
        }
    }


    /**
     * Arranges and returns the elements of a given row from the dataGameState's valuesTable as a formatted string.
     *
     * @param row The row index to be arranged.
     * @return A formatted string containing the elements of the specified row separated by spaces.
     */
    private fun arrangeRowElements(row: Int): String {
        val rowElements = StringBuilder()
        for (i in 0 until MAX_COLUMN) {
            // Append each element of the row with a space separator.
            rowElements.append(" ${dataGameState.valuesTable[row][i]}")
        }
        return rowElements.toString()
    }



    /**
     * Displays the 3x3 table using data from dataGameState's valuesTable.
     *
     * The table is printed row by row, with a horizontal border separating each row.
     */
    fun displayTable() {
        val topBottomBorder = "---------"
        var rowIndex = 0
        println(topBottomBorder)
        repeat(3) {
            // Print each row, wrapped with vertical borders, and then increment the rowIndex.
            println("|${arrangeRowElements(rowIndex)} |")
            rowIndex++
        }
        println(topBottomBorder)
    }


}