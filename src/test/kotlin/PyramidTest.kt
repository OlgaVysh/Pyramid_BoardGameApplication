import CardSuit.*
import CardValue.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PyramidTest {
    //Karten für die Pyramide werden erzeugt

    val card1 = Card(TWO, HEART, false)
    val card2 = Card(ACE, DIAMOND, false)

   //Listen für einzelnen Reihen der Pyramide werden erstellt
   //Normalerweise wiederholen sich die Karten nicht, hier wird es erstmal ignoriert

    var cardRow1 = mutableListOf(card1)
    var cardRow2 = mutableListOf(card1, card2)
    var cardRow3 = mutableListOf(card1, card2, card1)
    var cardRow4 = mutableListOf(card1, card2, card1, card2)
    var cardRow5 = mutableListOf(card1, card2, card1, card2, card1)
    var cardRow6 = mutableListOf(card1, card2, card1, card2, card1, card2)
    var cardRow7 = mutableListOf(card1, card2, card1, card2, card1, card2, card1)

    //Hier werden die Fehlermeldungen des Konstruktors überprüft
    @Test
    fun testPyramidSize()
    {
        //Leere Liste als Argument übergeben
        val null_exception = assertFailsWith<IllegalStateException>(
            block={ Pyramid(listOf())}
        )
        assertEquals("Pyramid should have exact 7 rows", null_exception.message)

            //Hier wird richtige Anzahl an Listen aber mit falscher Anzahl an Elementen übergeben

        val rows_exception = assertFailsWith < IllegalStateException >(
            block = { Pyramid(listOf(cardRow1, cardRow1, cardRow1, cardRow1, cardRow1, cardRow1, cardRow1)) }
        )
        //Fehler muss bei der zweiten Liste kommen
        assertEquals("The second row of pyramid should contain 2 cards", rows_exception.message)

        val last_row_exception = assertFailsWith<IllegalStateException>(
            block={ Pyramid(listOf(cardRow1, cardRow2, cardRow3, cardRow4, cardRow5, cardRow6, cardRow6))}
        )
        //Fehler muss bei der letzten Liste kommen
        assertEquals("The seventh row of pyramid should contain 7 cards", last_row_exception.message)
    }

    @Test
    fun testGetter()
    {
        val newPyramid = Pyramid (listOf(cardRow1, cardRow2, cardRow3, cardRow4, cardRow5, cardRow6, cardRow7))
        assertEquals(listOf(cardRow1, cardRow2, cardRow3, cardRow4, cardRow5, cardRow6, cardRow7), newPyramid.cards)
    }

    @Test
    fun testSetter()
    {
        val newPyramid = Pyramid (listOf(cardRow1, cardRow2, cardRow3, cardRow4, cardRow5, cardRow6, cardRow7))
        val newCardRow7 = mutableListOf(card2, card2, card2, card2, card2, card2, card2)

        newPyramid.cards= listOf(cardRow1, cardRow2, cardRow3, cardRow4, cardRow5, cardRow6, newCardRow7)
        assertEquals(listOf(cardRow1, cardRow2, cardRow3, cardRow4, cardRow5, cardRow6, newCardRow7), newPyramid.cards)
    }
}