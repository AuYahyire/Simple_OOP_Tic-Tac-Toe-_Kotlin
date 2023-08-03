package tictactoe

import kotlin.math.abs

/**
 * GameAnalyzer is a utility class used to analyze the state of a Tic-Tac-Toe game represented by a GameState object.
 * It calculates the values of rows, columns, and diagonals, and determines the current state of the game.
 *
 * @param dataGameState The GameState object representing the current state of the Tic-Tac-Toe game.
 */
class GameAnalyzer(private val dataGameState: GameState) {
    /**
     * Property that analyzes the game situation and prints the current state of the game.
     */
    val analyzeGame: Unit
        get() {
            val tableValues = calculateValuesOfFields()
            analyzeGameSituation(tableValues)
        }


    /**
     * Calculates the values of row, column, and diagonal sums.
     *
     * @return CalculationResults object containing the results of the sums.
     */
    private fun calculateValuesOfFields(): CalculationResults {
        val horizontalRowSums = sumHorizontalRows()
        val verticalColumnSums = sumVerticalColumns()
        val leftToRightDiagonalSum = sumLeftToRightDiagonalRow()
        val rightToLeftDiagonalSum = sumRightToLeftDiagonalRow()
        val totalCountXandO = countXAndO()

        return CalculationResults(
            horizontalRowSums,
            verticalColumnSums,
            leftToRightDiagonalSum,
            rightToLeftDiagonalSum,
            totalCountXandO
        )
    }

    /**
     * Analyzes the game situation based on the calculation results and updates the game state accordingly.
     *
     * @param tableValues CalculationResults object containing the results of the sums.
     */
    private fun analyzeGameSituation(tableValues: CalculationResults) {
        when {
            isGameImpossible(tableValues) -> dataGameState.currentState = CurrentState.IMPOSSIBLE
            xWins(tableValues) -> dataGameState.currentState = CurrentState.X_WINS
            oWins(tableValues) -> dataGameState.currentState = CurrentState.O_WINS
            isGameNotFinished() -> dataGameState.currentState
            isGameDraw(tableValues) -> dataGameState.currentState = CurrentState.DRAW
        }
    }

    /**
     * Checks if the game is in an impossible state (e.g., both X and O win, or one player has too many moves).
     *
     * @param tableValues CalculationResults object containing the results of the sums.
     * @return True if the game is impossible, otherwise false.
     */
    private fun isGameImpossible(tableValues: CalculationResults): Boolean {
        val totalX = tableValues.totalCountXAndO.first
        val totalO = tableValues.totalCountXAndO.second
        return when {
            abs(totalX - totalO) > 1 -> true
            xWins(tableValues) && oWins(tableValues) -> true
            else -> false
        }
    }

    /**
     * Checks if X wins the game based on the calculation results.
     *
     * @param tableValues CalculationResults object containing the results of the sums.
     * @return True if X wins, otherwise false.
     */
    private fun xWins(tableValues: CalculationResults): Boolean {
        val valueOfThreeX = VALUE_OF_THREE_X_IN_A_ROW
        return (0 until MAX_INDEX).any { index ->
                    tableValues.horizontalRowSums[index] == valueOfThreeX ||
                    tableValues.verticalColumnSums[index] == valueOfThreeX ||
                    tableValues.leftToRightDiagonalSum == valueOfThreeX ||
                    tableValues.rightToLeftDiagonalSum == valueOfThreeX
        }
    }

    /**
     * Checks if O wins the game based on the calculation results.
     *
     * @param tableValues CalculationResults object containing the results of the sums.
     * @return True if O wins, otherwise false.
     */
    private fun oWins(tableValues: CalculationResults): Boolean {
        val valueOfThreeO = VALUE_OF_THREE_O_IN_A_ROW
        return (0 until MAX_INDEX).any { index ->
            tableValues.horizontalRowSums[index] == valueOfThreeO ||
                    tableValues.verticalColumnSums[index] == valueOfThreeO ||
                    tableValues.leftToRightDiagonalSum == valueOfThreeO ||
                    tableValues.rightToLeftDiagonalSum == valueOfThreeO
        }
    }

    /**
     * Checks if the game is not finished yet checking if the table contains '_' characters.
     *
     * @return True if the game is not finished, otherwise false.
     */
    private fun isGameNotFinished(): Boolean {
        for (index in 0 until MAX_INDEX) {
            if (dataGameState.valuesTable[index].contains('_') || dataGameState.valuesTable[index].contains(' ')) {
                return true
            }
        }
        return false
    }

    /**
     * Checks if the game is a draw based on the calculation results.
     *
     * @param tableValues CalculationResults object containing the results of the sums.
     * @return True if the game is a draw, otherwise false.
     */
    private fun isGameDraw(tableValues: CalculationResults): Boolean {
        val valuesToCheck = listOf(VALUE_OF_THREE_X_IN_A_ROW, VALUE_OF_THREE_O_IN_A_ROW)
        return (0 until MAX_INDEX).none { index ->
            valuesToCheck.any { winValue ->
                        tableValues.horizontalRowSums[index] == winValue ||
                        tableValues.verticalColumnSums[index] == winValue ||
                        tableValues.leftToRightDiagonalSum == winValue ||
                        tableValues.rightToLeftDiagonalSum == winValue
            }
        }
    }

    /**
     * Calculates the sums of each horizontal row in the game table.
     *
     * @return A mutable list containing the sums of each horizontal row.
     */
    private fun sumHorizontalRows(): MutableList<Int> {
        val rowSums = mutableListOf<Int>()
        for (index in 0 until MAX_INDEX) {
            val rowSum = dataGameState.valuesTable[index].sumOf { char -> char.code }
            rowSums.add(rowSum)
        }
        return rowSums
    }

    /**
     * Calculates the sums of each vertical column in the game table.
     *
     * @return A mutable list containing the sums of each vertical column.
     */
    private fun sumVerticalColumns(): MutableList<Int> {
       // List to store sum columns
        val columnSums = MutableList(dataGameState.valuesTable[0].size) { 0 }

        for (row in dataGameState.valuesTable) { //Accessing to rows one per one
            for ((index, value) in row.withIndex()) { //Retrieving in pairs the index and content of each element
                columnSums[index] += value.code
            }
        }
        return columnSums
    }

    /**
     * Calculates the sum of the left-to-right diagonal in the game table.
     *
     * @return The sum of the left-to-right diagonal.
     */
    private fun sumLeftToRightDiagonalRow(): Int {
        var leftToRightDiagonalRowSum = 0
        var index = 0
        var char = 0
        while (index < MAX_INDEX) {
            leftToRightDiagonalRowSum += dataGameState.valuesTable[index][char].code
            index++
            char++
        }
        return leftToRightDiagonalRowSum
    }

    /**
     * Calculates the sum of the right-to-left diagonal in the game table.
     *
     * @return The sum of the right-to-left diagonal.
     */
    private fun sumRightToLeftDiagonalRow(): Int {
        var rightToLeftDiagonalRowSum = 0
        var index = 0
        var char = 2
        while (index < MAX_INDEX) {
            rightToLeftDiagonalRowSum += dataGameState.valuesTable[index][char].code
            index++
            char--
        }
        return rightToLeftDiagonalRowSum
    }

    /**
     * Counts the number of 'X' and 'O' in the game table.
     *
     * @return A pair representing the count of 'X' and 'O' respectively.
     */
    private fun countXAndO(): Pair<Int, Int> {
        var quantityOfX = 0
        var quantityOfO = 0
        for (i in 0 until MAX_INDEX) {
            for (j in 0 until MAX_INDEX)
                when(dataGameState.valuesTable[i][j]) {
                    'X' -> quantityOfX++
                    'O' ->  quantityOfO++
                    else -> continue
                }
        }
        return quantityOfX to quantityOfO
    }
}