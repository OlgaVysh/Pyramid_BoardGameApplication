import CardSuit.*
import CardValue.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PyramideTest {

    //Karten für die Pyramide werden erzeugt
    val card1 = Card(TWO, HEART, false)
    val card2 = Card(ACE, DIAMOND, false)

    //Normalerweise wiederholen sich die Karten nicht, hier wird es erstmal ignoriert

    //Listen für einzelnen Reihen der Pyramide werden erstellt
    var cardRow1 = mutableListOf(card1)
    var cardRow2 = mutableListOf(card1, card2)
    var cardRow3 = mutableListOf(card1, card2, card1)
    var cardRow4 = mutableListOf(card1, card2, card1, card2)
    var cardRow5 = mutableListOf(card1, card2, card1, card2, card1)
    var cardRow6 = mutableListOf(card1, card2, card1, card2, card1, card2)
    var cardRow7 = mutableListOf(card1, card2, card1, card2, card1, card2, card1)

    //Pyramide wird erstellt
    val newPyramid = Pyramid (listOf(cardRow1, cardRow2, cardRow3, cardRow4,
                                     cardRow5, cardRow6, cardRow7))

    //Stapel werden erstellt
    var drawStack = CardStack(cardRow7)
    var reserveStack = CardStack(mutableListOf<Card>())

    //Liste aus 52 Karten für den Spiel wird erstellt
    var gameCards = mutableListOf(card1, card2, card1, card2, card1, card2, card1, card1,
                                  card2, card1, card2, card1, card2, card1, card1, card1,
                                  card1, card2, card1, card2, card1, card2, card1, card1,
                                  card2, card1, card2, card1, card2, card1, card1, card1,
                                  card1, card2, card1, card2, card1, card2, card1, card1,
                                  card2, card1, card2, card1, card2, card1, card1, card1,
                                  card1, card2, card1, card2)

    //Players werden erstellt
    val player1 = Player("Olga")
    val player2 = Player("Timm")
    val new_players = listOf(player1, player2)

    //Exceptions vom Konstruktor werden getestet
    @Test //Ungültiger aktueller Player-Index löst eine Exception aus
    fun test_currentPlayer_Value()
    {
        val exception = assertFailsWith<IllegalStateException>(
            block={ Pyramide(false, 5, new_players,
                            gameCards, drawStack, reserveStack, newPyramid)}
        )
        assertEquals("invalid currentPlayer index", exception.message)
    }

    @Test
    fun testPlayers() //Mehr als 2 Spieler wurden übergeben
    {
        val exception = assertFailsWith<IllegalStateException>(
            block={ Pyramide(false, 1, listOf(player1,player2,Player("Kate")),
                gameCards, drawStack, reserveStack, newPyramid)}
        )
        assertEquals("invalid players count", exception.message)
    }

    @Test
    fun testCards() //Die Liste aus nur 2 Karten wurde übergeben
    {
        val exception = assertFailsWith<IllegalStateException>(
            block={ Pyramide(false, 1, new_players,
                listOf(card1, card2), drawStack, reserveStack, newPyramid)}
        )
        assertEquals("invalid cards count", exception.message)
    }

    //Getter wird getestet

    @Test
    fun testGetter()
    {
        val pyramide = Pyramide(false, 1, new_players,
            gameCards, drawStack, reserveStack, newPyramid)

        assertEquals(false, pyramide.opponentPassed)
        assertEquals(1, pyramide.currentPlayer)
        assertEquals(new_players, pyramide.players)
        assertEquals(gameCards, pyramide.cards)
        assertEquals(drawStack, pyramide.drawStack)
        assertEquals(reserveStack, pyramide.reserveStack)
        assertEquals(newPyramid, pyramide.pyramid)
    }

    //Setter wird getestet

    @Test
    fun testSetter()
    {
        val pyramide = Pyramide(false, 1, new_players,
            gameCards, drawStack, reserveStack, newPyramid)

        pyramide.opponentPassed=true
        pyramide.currentPlayer=2

        assertEquals(true, pyramide.opponentPassed)
        assertEquals(2, pyramide.currentPlayer)

    }
}