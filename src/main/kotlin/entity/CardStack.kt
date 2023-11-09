package entity
import kotlin.random.Random
/**
 * Data structure that holds [Card] objects and provides stack-like access to them.
 * @param cards is a list of Cards to be put on the stack
 */


class CardStack (val cards: ArrayDeque<Card> = ArrayDeque()) {




    /**
     * @property size is the amount of cards in this stack
     */
    val size: Int get() = cards.size

    /**
     * @property empty Returns `true` if the stack is empty, `false` otherwise.
     */
    val empty: Boolean get() = cards.isEmpty()

    /**
     * puts a list of cards on the stack, last element ist on top
     */
    fun putOnTop(cards: List<Card>) {
        cards.forEach(this.cards::addFirst)
    }

    /**
     * puts a card on top of the stack
     */
    fun putOnTop(card: Card) {
        cards.addFirst(card)
    }

    /**
     *  output method
     */
    override fun toString(): String = cards.toString()

}