package tictactoe

fun main() {
   //Start creating the table state based on the user input string
   val gameTable = GameTable()

  // Continuously execute the game until it reaches the finished state
   while (gameTable.dataGameState.currentState == CurrentState.GAME_NOT_FINISHED) {
      gameTable.executeGame
   }

   // Display the final state of the game
   println(gameTable.dataGameState.currentState.stateToDisplay)
}