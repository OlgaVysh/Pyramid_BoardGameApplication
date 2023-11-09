import org.junit.jupiter.api.Test
import entity.Card
import entity.CardSuit
import entity.CardValue
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class CardTest {

    //Da entity.Card eine data Klasse ist, werden hier neben dem Getter und Setter noch die vom Konstruktor
    //zur Verfügung gestellte data Methoden getestet

    //Hier werden 2 Karten erstellt
    var card1 = Card(CardValue.TWO, CardSuit.HEARTS)
    var card2 = Card(CardValue.TWO, CardSuit.DIAMONDS)

    @Test
    fun testGetter()
    {
        val card1Value = card1.cardValue
        assertEquals(CardValue.TWO, actual = card1Value)

        val card1Suit = card1.cardSuit
        assertEquals(CardSuit.HEARTS, actual = card1Suit)

        assertEquals(false, actual = card1.isRevealed)
    }

    @Test
    fun testSetter()
    {
        val card3 = Card(CardValue.TWO, CardSuit.HEARTS)
        assertEquals(false, actual = card3.isRevealed)
        card3.isRevealed = true
        assertEquals(true, actual = card3.isRevealed)
    }

    @Test //Überprüfe das Vergleichen der Karten ( Ergebnis soll falsch sein )
    fun testEqual()
    {
    assertTrue(card1 != card2)
    }

    @Test //Überprüfe die Methode toString()
    fun testToString()
    {
        val expected = "HEARTS:2"
        val result = card1.toString()
        assertEquals(expected, result)
    }

    @Test //Überprüfe die Methode copy()
    fun testCopy()
    {
        val card1Copy= card1.copy() //gleichwertige Kopie wird erstellt
        assertNotSame(card1, card1Copy)
        assertEquals(card1, card1Copy)

        val card1AlteredCopy = card1.copy(cardValue = CardValue.FOUR) //Beim Kopieren wird cardValue geändert
        assertTrue(card1 != card1AlteredCopy)
    }

    @Test
    fun testHashcode() //Überprüfe die Methode hashCode()
    {
        val card1Copy= card1.copy() //Karten gleich - hashCodes gleich
        assertEquals(card1.hashCode(),card1Copy.hashCode())

        val card1Copy2= card1.copy(cardValue = CardValue.FOUR) //Karten ungleich
        assertNotEquals(card1.hashCode(), card1Copy2.hashCode())
    }
}