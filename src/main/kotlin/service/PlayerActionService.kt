package service

import entity.*
/**
 * Service layer class that provides the logic for the two possible actions a player
 * can take in the game: pass, reveal card from a draw stack or remove two cards.
 */

class PlayerActionService (private val rootService: RootService) : AbstractRefreshingService(){

    /**
     * represents the activity pass in the game. If the opponent already passed, endGame()
     * will be executed. Otherwise, opponentPassed will be set at true and changePlayer() will
     * be executed.
     */
    fun pass( ) {
        val game = rootService.currentGame
        checkNotNull(game) { "No game started yet."}
        if(!game.opponentPassed)
        {
            game.opponentPassed = true
            rootService.gameService.changePlayer()
        }

        else rootService.gameService.endGame()
        onAllRefreshables { refreshAfterPass() }
    }

    /**
     *  represents the activity removePair in the game.
     *  @param card1 is the first chosen card
     *  @param card2 is the second chosen card
     *  If the cards are valid - the values sum is 15 and max one of cards is an ACE,
     *  each card will be identified as card from the reserve stack or card from pyramid and removed.
     *  If the paar was successfully removed, players score will be increased by one point.
     */
    fun removePair(card1 : Card, card2 : Card) {
        val game = rootService.currentGame
        checkNotNull(game) { "No game started yet." }

        require(rootService.gameService.checkCardChoice(card1, card2)) { "Invalid cards choice" }

        var card1_isValid = false
        var card2_isValid = false

        val cardFromStack = game.reserveStack.cards.first()
        //erste Karte vom reserveStack, die zweite aus Pyramide
        if (card1 == cardFromStack) {
            card1_isValid = game.reserveStack.cards.remove(card1)

            val cardsIterator = game.pyramid.cards.iterator()

            while (cardsIterator.hasNext() && !card2_isValid) {
                card2_isValid = cardsIterator.next().remove(card2)
            }


        }
        //Die zweite Karte vom reserveStack, die erste aus Pyramide
        else if (card2 == cardFromStack) {
            card2_isValid = game.reserveStack.cards.remove(card2)

            val cardsIterator = game.pyramid.cards.iterator()

            while (cardsIterator.hasNext() && !card1_isValid) {
                card1_isValid = cardsIterator.next().remove(card1)
            }

        } else //Beide Karten aus der Pyramide
        {
            val cardsIterator = game.pyramid.cards.iterator()
            while (cardsIterator.hasNext() && !(card1_isValid && card2_isValid)) {
                card1_isValid = cardsIterator.next().remove(card1)
                card2_isValid = cardsIterator.next().remove(card2)
            }


        }

        if (card1_isValid && card2_isValid) //wenn beide Karten erfolgreich entfern wurden
        {
            val currentPlayer = rootService.currentGame!!.currentPlayer
            rootService.currentGame!!.players[currentPlayer - 1].score += rootService.gameService.setScore(card1, card2)

            //Beende das Spiel, falls die Pyramide leer ist
            if (rootService.gameService.checkEmptyPyramid(game.pyramid)) rootService.gameService.endGame()
            else {
                rootService.gameService.flipCard() //Pyramide anpassen
                rootService.currentGame!!.opponentPassed = false //es wurde nicht gepasst

                onAllRefreshables { refreshAfterRemovePair(card1_isValid && card2_isValid) }
                rootService.gameService.changePlayer()
            }
        }
    }

    /**
     * represents the activity revealCard in the game. If the draw stack is not empty, player can
     * draw the top card from the stack, flip it and put it (revealed) on a reserve stack.
     */
    fun revealCard() {

        val game = rootService.currentGame
        checkNotNull(game) { "No game started yet."}

        if (!game.drawStack.empty)
        {
            val card = game.drawStack.cards.first()
            card.isRevealed = true
            game.reserveStack.cards.addFirst(card)
            game.drawStack.cards.removeFirst()

            rootService.currentGame!!.opponentPassed=false
            rootService.gameService.changePlayer()
        }

        onAllRefreshables { refreshAfterRevealCard() }

    }
}
