package service
import entity.*
import org.junit.jupiter.api.Assertions.assertNotNull
import kotlin.test.*

/**
 * Class that provides tests for [PlayerActionService]
 */
class PlayerServiceTest {
    /**
     * Starts a game using startGame(player1Name:String, player2Name: String) method from GameService. Creates cards, stack and pyramid that can be used
     * in other tests to deterministically validate the outcome of turns.
     * Uses and therefore tests private methods distributeCards(), createDrawStack() and createPyramid()
     * Additionally : Prints list of cards, drawStack and pyramid to visualize the structures.
     * @return the root service holding the started game as [RootService.currentGame]
     */



    private fun startGame() : RootService
    {
        val mc = RootService()
        mc.gameService.startGame("Bob", "Alice")
        assertNotNull(mc.currentGame)
        //println("cards are : " + mc.currentGame?.cards.toString())
        //println("drawStack is : " + mc.currentGame?.drawStack.toString())
        //println("pyramid is : " + mc.currentGame?.pyramid.toString())
        return mc
    }

    /**
     * Tests methods Pass()
     */
    @Test
    fun testPass()
    {
        val game = startGame()
        val currentPlayer = game.currentGame!!.currentPlayer
        assertFalse(game.currentGame!!.opponentPassed) //am Anfang des Spiels hat noch keiner gepasst

        game.playerActionService.pass()

        assertTrue(game.currentGame!!.opponentPassed) //es wurde einmal gepasst
        assertNotEquals(currentPlayer, game.currentGame!!.currentPlayer) //teste, ob spieler gewechselt wurde

        //wenn es zum 2en mal gepasst wurde, wird endGame() aufgerufen
        //da beide Spieler keine Punkte gesammelt haben, muss das Spiel mit Gleichstand enden
        //das konnte ich noch nicht implementieren, endGame() wird zwar aufgerufen und liefert das Ergebnis,
        //aber ich kann es nicht abfangen
        val end = game.playerActionService.pass().toString()
        //assertEquals("It's a tie! Score Player 1 : 0, score Player 2 : 0",end)

    }


    /**
     * Tests method removePair()
     */
    @Test
    fun testRemovePair()
    {
        val game = startGame()
        val currentPlayer = game.currentGame!!.currentPlayer

        //Karten für die Eingabe erstellen

        //zwei Karten aus der Pyramide
        val card1 = game.currentGame!!.pyramid.cards.elementAt(3).last()
        val card2 = game.currentGame!!.pyramid.cards.elementAt(5).first()
        //die oberste Karte vom drawStack
        val card3 = game.currentGame!!.drawStack.cards.first()


        //2 Karten aus der Pyramide testen
        if(game.gameService.checkCardChoice(card1,card2)) //wenn Kartenauswahl valide (1 ACE oder summe 15)
        {
            game.playerActionService.removePair(card1, card2)
            assertFalse(game.currentGame!!.pyramid.cards.elementAt(3).contains(card1)) //card1 wurde entfernt
            assertFalse(game.currentGame!!.pyramid.cards.elementAt(5).contains(card2)) //card2 wurde entfernt

            //Karten wurden umgedreht
            assertTrue(game.currentGame!!.pyramid.cards.elementAt(3).last().isRevealed)
            assertTrue(game.currentGame!!.pyramid.cards.elementAt(5).first().isRevealed)

            //opponentPassed wurde auf false gesetzt und player wurde gewechslet
            assertFalse(game.currentGame!!.opponentPassed)
            assertNotEquals(currentPlayer, game.currentGame!!.currentPlayer)
        }

        else //wenn Kartenauswahl invalide (2 ACE oder summe!= 15)
        {
            val exception = assertFailsWith<IllegalArgumentException>(
                block={  game.playerActionService.removePair(card1, card2)}
            )
            assertEquals("Invalid cards choice", exception.message)
        }

        //eine Karte aus der Pyramide und eine aus dem drawStack testen
        if (game.gameService.checkCardChoice(card1,card3))
        {
            game.playerActionService.removePair(card1, card3)
            assertFalse(game.currentGame!!.pyramid.cards.elementAt(3).contains(card1)) //card1 wurde entfernt
            assertFalse(game.currentGame!!.drawStack.cards.contains(card3)) //card3 wurde entfernt

            //Karten wurden umgedreht
            assertTrue(game.currentGame!!.pyramid.cards.elementAt(3).last().isRevealed)
            assertFalse(game.currentGame!!.drawStack.cards.first().isRevealed)

            //opponentPassed wurde auf false gesetzt und player wurde gewechslet
            assertFalse(game.currentGame!!.opponentPassed)
            assertNotEquals(currentPlayer, game.currentGame!!.currentPlayer)
        }
        else
        {
            val exception = assertFailsWith<IllegalArgumentException>(
            block={  game.playerActionService.removePair(card1, card3)}
            )
            assertEquals("Invalid cards choice", exception.message)
        }

    }


    /**
     * Tests methods revealCard()
     */
    @Test
    fun testRevealCard()
    {
        val game = startGame()

        val currentPlayer = game.currentGame!!.currentPlayer

        assertFalse(game.currentGame!!.drawStack.empty) //DrawStack nicht leer

        //Größen der Stacks holen, um später zu vergleichen
        val sizeDraw = game.currentGame!!.drawStack.size
        val sizeReserve = game.currentGame!!.reserveStack.size

        val card = game.currentGame!!.drawStack.cards.first()
        game.playerActionService.revealCard()
        //Karte ist im Reservestapel ganz oben
        assertEquals(card, game.currentGame!!.reserveStack.cards.first())
        //Karte wurde gedreht
        assertTrue(game.currentGame!!.reserveStack.cards.first().isRevealed)

        //Die Größen von Stacks haben sich geändert
        assertNotEquals(sizeDraw, game.currentGame!!.drawStack.size)
        assertNotEquals(sizeReserve, game.currentGame!!.reserveStack.size)
       //Die Karte ist nicht mehr im DrawStack aber ist im ReserveStack
        assertFalse(game.currentGame!!.drawStack.cards.contains(card))
        assertTrue(game.currentGame!!.reserveStack.cards.contains(card))

        //opponentPassed wurde auf false gesetzt und player wurde gewechslet
        assertFalse(game.currentGame!!.opponentPassed)
        assertNotEquals(currentPlayer, game.currentGame!!.currentPlayer)
    }
}