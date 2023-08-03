package tictactoe

/**Data class which holds the current state of the tic-tac-toe game*/
data class GameState(
    /**2D table containing the game table values*/
    val valuesTable: MutableList<MutableList<Char>>,
    /**State of the game, not finished until otherwise state.*/
    var currentState: CurrentState,
    /**Current player playing, changed by turn from X to O and viceversa*/
    var currentPlayer: Char
) {
    companion object {
        /**Default game state*/
        val initialState = GameState(
            valuesTable = mutableListOf(
                mutableListOf(),
                mutableListOf(),
                mutableListOf()
            ),
            currentState = CurrentState.GAME_NOT_FINISHED,
            currentPlayer = 'O'
        )
    }
}

/**
 * Enumeration representing the possible states of the tic-tac-toe game.
 *
 * @param stateToDisplay The string representation of the state to display.
 */
enum class CurrentState(val stateToDisplay: String){
    GAME_NOT_FINISHED("Game not finished"),
    DRAW("Draw"),
    X_WINS("X wins"),
    O_WINS("O wins"),
    IMPOSSIBLE("Impossible")
}

/**
 * Data class representing the results of calculations for the tic-tac-toe game.
 *
 * @param horizontalRowSums List containing the sums of each horizontal row.
 * @param verticalColumnSums List containing the sums of each vertical column.
 * @param leftToRightDiagonalSum The sum of the left-to-right diagonal elements.
 * @param rightToLeftDiagonalSum The sum of the right-to-left diagonal elements.
 * @param totalCountXAndO Pair containing the total count of 'X' and 'O' symbols in the game table.
 */
data class CalculationResults(
    val horizontalRowSums: List<Int>,
    val verticalColumnSums: List<Int>,
    val leftToRightDiagonalSum: Int,
    val rightToLeftDiagonalSum: Int,
    val totalCountXAndO: Pair<Int, Int>
)
