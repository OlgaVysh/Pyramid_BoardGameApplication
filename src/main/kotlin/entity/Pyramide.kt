package entity

/**
 *The class represented the game itself.
 * @property players is a list of two player-Objects : players of the current game
 * @property cards  is the List of the cards in the game (amount: 52)
 * @property drawStack  is the Stack of 24 unrevealed cards
 * from which players can draw one card at a time and put it on the top of reverse stack
 * @property reserveStack is a stack players fill with revealed cards and from which they
 * can choose the top card to remove
 * @property pyramid is the card pyramid
 * @property currentPlayer is a flag which tell which player is active
 *                          (currentPlayer == 1 for player1 and currentPlayer == 2 for player2 )
 */
class Pyramide (var opponentPassed : Boolean = false, var currentPlayer : Int=1, val players : List<Player>,
                val cards : List<Card>, val drawStack : CardStack,
                val reserveStack : CardStack,
                val pyramid : Pyramid
) {

    /**
     * The constructor checks whether valid arguments have been passed
     * for the construction of the game. There scThe constructor checks whether valid arguments
     * have been passed for the construction of the pyramid game.
     * There should be two players and 52 cards
     */
    init {

        //es gibt genau 2 Players im Spiel
        require(players.size==2){"invalid players count"}

        //es gibt genau 52 Karten im Spiel
        require(cards.size==52){"invalid cards count"}
    }


}