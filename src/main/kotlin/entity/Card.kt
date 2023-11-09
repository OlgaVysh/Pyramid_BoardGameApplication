package entity



/** the data class for the element card
 * @property CardSuit Describe the Suit of the entity.Card with the four suit
 * @property CardValue represented the Value of card (2-10, Ace ,Jack ,Queen , King)
 */


data class Card(val cardValue : CardValue, val cardSuit : CardSuit){

    /**
     *  @property isRevealed  A flag for the card, if the card is revealed or not.
     *  All cards are in the beginning are not revealed
     */
    var isRevealed = false

    /**
     *  output method
     */
    override fun toString() = "$cardSuit:$cardValue"

    /**
     *  this method calculates the sum of the two card values ordinals
     *  Is used in GameService to check weather sum is 15
     */


}
