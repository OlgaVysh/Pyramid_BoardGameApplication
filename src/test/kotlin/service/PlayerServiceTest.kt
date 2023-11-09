package service
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
        assertFalse(game.currentGame!!.opponentPassed) //am Anfang des Spiels hat noch keiner gepasst

        game.playerActionService.pass()
        assertTrue(game.currentGame!!.opponentPassed) //es wurde einmal gepasst
        //wenn es zum 2en mal gepasst wurde, wird endGame() aufgerufen
        //da beide Spieler keine Punkte gesammelt haben, muss das Spiel mit Gleichstand enden
        //das konnte ich noch nicht implementieren, endGame() wird zwar aufgerufen und liefert das Ergebnis,
        //aber ich kann es nicht abfangen

        val end = game.playerActionService.pass().toString()
        //assertEquals("It's a tie! Score Player 1 : 0, score Player 2 : 0",end)

    }

    /**
     * Tests methods removePair()
     */
    @Test
    fun testRemovePair()
    {
        //noch nicht geschafft
    }

    /**
     * Tests methods revealCard()
     */
    @Test
    fun testRevealCard()
    {
        val game = startGame()

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
    }
}