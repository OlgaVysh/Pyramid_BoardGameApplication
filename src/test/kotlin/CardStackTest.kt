import org.junit.jupiter.api.Test
import entity.*
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertSame
import kotlin.random.Random


class CardStackTest {
    private val c1 = Card(CardValue.ACE, CardSuit.SPADES)
    private val c2 = Card(CardValue.JACK, CardSuit.CLUBS)
    private val c3 = Card(CardValue.QUEEN, CardSuit.HEARTS)

    /**
     * Ensure stack behavior (i.e., if cards put on top are drawn/peeked next)
     * Also tests if putting a list on top results in the last card of this list
     * being on top of the stack afterwards.
     */
    @Test
    fun testOrder() {

        val stack = CardStack()

        stack.putOnTop(listOf(c1, c2, c3))

        val card = stack.cards.first()
        assertSame(card, c3)
        stack.cards.remove(card)
        assertSame(stack.cards.first(), c2)
        assertEquals(stack.size, 2)

        stack.putOnTop(c3)
        assertSame(stack.cards.first(), c3)
        assertEquals(stack.size, 3)

    }

    /**
     * Test if shuffle works
     */


    /**
     * Test if drawing from an empty stack throws an exception
     */






}