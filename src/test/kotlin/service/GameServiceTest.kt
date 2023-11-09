package service
import entity.*
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Class that provides tests for [GameService]
 */

class GameServiceTest {

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
     * Tests method startGame(player1Name:String, player2Name: String) with an empty
     * players name. IllegalArgumentException with message "Please enter Username" must be thrown.
     */

    @Test
fun testStartGame_Null()
{
  val name = ""
    val mc = RootService()
    val exception =assertFailsWith<IllegalArgumentException>(
        block={ mc.gameService.startGame(name, "Alice") }
    )
    assertEquals("Please enter Username",exception.message)
}

    /**
     * Tests method changePlayer()
     * Changes Player two times to check if the shifts of currentPlayer from 1 to 2 and back work correctly
     */
@Test
fun testChangePlayer()
{
    val game = startGame()
    //check if currentPlayer is set correctly
    assertEquals(1, game.currentGame?.currentPlayer)

    game.gameService.changePlayer()
    assertEquals(2, game.currentGame?.currentPlayer)

    game.gameService.changePlayer()
    assertEquals(1, game.currentGame?.currentPlayer)
}

    /**
     * Tests method checkCardChoice(card1 : Card, card2 : Card) with 4 possible combinations :
     * sum is 15 but both are aces - returns false
     * sum is not 15 - returns false
     * sum is 15 and one card is ace - returns true
     * sum is 15 without aces - returns true
     */
    //Diese Methode funktioniert noch nicht daher einkommentiert
    @Test
    fun testCheckCardChoice()
    {
        val game = startGame()

        //Summe 15, aber beide ACE
        val card1 = Card(CardValue.ACE, CardSuit.HEARTS)
        val card2 = Card(CardValue.ACE, CardSuit.DIAMONDS)
        assertFalse { game.gameService.checkCardChoice(card1,card2) }

        //Summe nicht 15
        val card3 = Card(CardValue.TWO, CardSuit.HEARTS)
        val card4 = Card(CardValue.EIGHT, CardSuit.HEARTS)
        assertFalse { game.gameService.checkCardChoice(card3,card4) }

        //ein Ace
        val card5 = Card(CardValue.ACE, CardSuit.HEARTS)
        val card6 = Card(CardValue.TWO, CardSuit.HEARTS)
        assertTrue { game.gameService.checkCardChoice(card5,card6) }

        //Summe 15 ohne ACE
        val card7 = Card(CardValue.TWO, CardSuit.HEARTS)
        val card8 = Card(CardValue.KING, CardSuit.DIAMONDS)
        assertTrue { game.gameService.checkCardChoice(card7,card8) }
    }

    /**
     * Tests methods endGame(),which uses and therefore tests private method showResult()
     * Results will be calculated and returned for three cases: Player1 is winner, player2 is winner or it's a tie
     */
    @Test
    fun testEndGame()
    {
        val game = startGame()
        //at the beginning both scores should be 0
        assertEquals(0,game.currentGame!!.players[0].score)
        assertEquals(0,game.currentGame!!.players[1].score)

        //set scores player1 > player2
        game.currentGame!!.players[0].score=10
        game.currentGame!!.players[1].score=7

        assertEquals("Winner is Bob! Score Player 1 : 10, score Player 2 : 7", game.gameService.endGame())

        //set scores player1 < player2
        game.currentGame!!.players[1].score=17

        assertEquals("Winner is Alice! Score Player 1 : 10, score Player 2 : 17", game.gameService.endGame())

        //set scores player1 = player2
        game.currentGame!!.players[1].score=10

        assertEquals("It's a tie! Score Player 1 : 10, score Player 2 : 10", game.gameService.endGame())

    }

   //Method flipCard() will be tested in class TestPlayerActionService as part from the method removePair()

}