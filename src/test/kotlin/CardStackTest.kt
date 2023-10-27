import org.junit.jupiter.api.Test
import CardSuit.*
import CardValue.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CardStackTest {
    //2 Karten werden erzeugt

    val card1 = Card(TWO, HEART, false)
    val card2 = Card(ACE, DIAMOND, false)

    //Listen für die Stapel werden erzeugt
    var listOfCards = mutableListOf(card1, card2)
    var emptyList = mutableListOf<Card>()

    //Nachziehstapel und Reservestapel werden erzeugt
    var drawStack = CardStack(listOfCards)
    var reserveStack = CardStack(emptyList)

    @Test
    fun testGetter()
    {
        val returnedCards = drawStack.cards
        val expected = mutableListOf(card1, card2)
        assertEquals(expected, returnedCards)

        assertEquals(mutableListOf<Card>(), reserveStack.cards)
    }

    @Test
    fun testSetter()
    {
        val newCards = mutableListOf(card1, card2)
        drawStack. cards = newCards
        assertEquals(newCards, drawStack.cards)
    }

    //Liste aus 25 Karten wird als Argument übergeben, um zu testen, ob ein Fehler getriggert wird
    @Test
    fun testStackLength()
    {
        assertFailsWith<IllegalStateException>(
            block= {
                CardStack( mutableListOf(
                    card1, card2, card1, card2, card1, card1, card2, card1, card2, card1,
                    card1, card2, card1, card2, card1, card1, card2, card1, card2, card1,
                    card1, card2, card1, card2, card1) )


            }
        )
    }

}