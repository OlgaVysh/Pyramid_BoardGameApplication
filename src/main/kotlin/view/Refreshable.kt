package view
/**
 * This interface provides a mechanism for the service layer classes to communicate
 * to the view classes that certain changes have been made to the entity
 * layer, so that the user interface can be updated accordingly.
 */

interface Refreshable {
    /**
     * perform refreshes that are necessary after a player has passed his/her turn
     */
    fun refreshAfterPass() {}

    /**
     * perform refreshes that are necessary after the attempt to remove cards from the game
     * @param isValid evaluation for the card choice
     */
    fun refreshAfterRemovePair(isValid : Boolean) {}

    /**
     * perform refreshes that are necessary after a player has drawn a card from draw stack
     * and put it on the reserve stack
     */
    fun refreshAfterRevealCard() {}

    /**
     * perform refreshes that are necessary after a player made a move and the next player is on
     */
    fun refreshAfterChangePlayer() {}

    /**
     * perform refreshes that are necessary after a new game started
     */
    fun refreshAfterStartGame() {}

    /**
     * perform refreshes that are necessary after the last round was played
     */
    fun refreshAfterEndGame() {}

    /**
     * perform refreshes that are necessary after card(s) was removed from pyramid
     */
    fun refreshAfterFlipCard() {}

}