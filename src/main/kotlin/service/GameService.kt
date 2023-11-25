package service
import entity.*

/**
 * Service layer class that provides the logic for actions not directly
 * related to a single player.
 */

class GameService (private val rootService: RootService) : AbstractRefreshingService(){

    /**
     *  Creates a current game = creates inputs for the constructor of entity Pyramide,
     *  which represents the game on an entity level.
     *  @param player1Name is the name of the first player
     *  @param player2Name is the name of the second player
     */
    fun startGame( player1Name:String, player2Name: String) {
        val players = listOf ( Player(player1Name), Player(player2Name)) //List<Player> erstellen
        val cards = distributeCards() // Liste aus 52 gemischte Karten erstellen
        val cardsPyramid = cards.dropLast(24) // erste 28 Karten aus der Liste für die Pyramide
        val cardsDrawStack = cards.drop(28) //letzte 24 Karten aus der Liste für drawStack
        val drawStack = createDrawStack(cardsDrawStack)
        val reserveStack = createDrawStack(listOf<Card>()) //reserveStack ist zu Beginn leer
        val pyramid = createPyramid(cardsPyramid)
        val game = Pyramide(false, 1, players, cards, drawStack, reserveStack, pyramid)
        rootService.currentGame = game
        onAllRefreshables { refreshAfterStartGame() }
    }

    /**
     *  Changes the indicator of a current player to it's index after the player is done with
     *  his/her move
     */
    fun changePlayer( ) {
        val game = rootService.currentGame
        checkNotNull(game) { "No game started yet."}
        game.currentPlayer = 3 - game.currentPlayer
        onAllRefreshables { refreshAfterChangePlayer() }

    }

    /**
     *  Checks whether card choice of two cards to remove is valid: cards are flipped, the value-sum is 15
     *  and max one of the cards is an ACE.
     *  Returns false, if both cards have the same value, because then there are two ACES oe sum is not 15
     *  Returns true, if one of cards is an ACE, because then the sum is always 15
     *  If both cards are not ACES, check weather sum is 15 and return the result(boolean)
     *  @param card1 is the first chosen card
     *  @param card2 is the second chosen card
     */
    fun checkCardChoice(card1 : Card, card2 : Card):Boolean
    {
        if(! (card1.isRevealed&&card2.isRevealed )) return false

        else{
        val card1Value = card1.cardValue
        val card2Value = card2.cardValue

        if(card1Value == card2Value) return false
        if ( (CardValue.ACE == card1Value).xor(card2Value == CardValue.ACE)) return true

        //Returns the ordinal of this enumeration constant
        //(its position in its enum declaration,
        // where the initial constant is assigned an ordinal of zero)
        //cardValue.ordinal +2 maps the int of card values : CardValue.TWO.ordinal = 0 and int value is 2

        val card1IntValue = card1Value.ordinal+2
        val card2IntValue = card2Value.ordinal+2

        return card1IntValue + card2IntValue==15}

    }



    /**
     *  Returns the outcome of the game and players scores
     */
    fun endGame(){
        val game = rootService.currentGame
        checkNotNull(game) { "No game currently running."}
        showResult()
        onAllRefreshables { refreshAfterEndGame() }


    }

    /**
     *  Flips the cards at the edge of the pyramid : the first and last card of every row
     */
    fun flipCard()
    {
        val game = rootService.currentGame
        checkNotNull(game) { "No game started yet."}

        val pyramid = game.pyramid.cards
        pyramid.forEach{
            val list = it
            if(list.isNotEmpty())
            {
                list.first().isRevealed=true
                list.last().isRevealed=true
            }
        }
    }


    /**
     *  checks if a pyramid is empty and returns a boolean
     */
    fun checkEmptyPyramid( p : Pyramid) : Boolean
    {
        val cardsIterator = p.cards.iterator()

        var pyramidEmpty = cardsIterator.next().isEmpty()
        while (cardsIterator.hasNext() && pyramidEmpty)
        {
            pyramidEmpty = pyramidEmpty && cardsIterator.next().isEmpty()

        }
        return pyramidEmpty
    }

    /**
     *  Returns an int value by which player's score must be increased.
     *  Is only called by the method removePair() (PlayerActionService) after the cards are approved to be removed by
     *  the method checkPair().
     *  If one of the cards is ACE, score will be increased by one point, otherwise by two.
     *  @param card1 is the first removed card
     *  @param card2 is the second removed card
     */
    fun setScore(card1 : Card, card2 : Card) : Int
    {
        val card1Value = card1.cardValue
        val card2Value = card2.cardValue
        return if ( (CardValue.ACE == card1Value).xor(card2Value == CardValue.ACE)) 1
        else 2
    }

    /**
     *  calculates the outcome of the game it returns it as a String. If there's a winner, his/her name
     *  will be returned. If both players have the same score, "it's a tie" will
     *  be returned as a result.
     */
    private fun showResult() : String
    {
        val game = rootService.currentGame
        checkNotNull(game) { "No game started yet."}

        val scorePlayer1 =  game.players[0].score
        val scorePlayer2 =  game.players[1].score

        var result = ""

        if(scorePlayer1<scorePlayer2)  result= "Winner is " + game.players[1].name + "!"
        else if (scorePlayer1>scorePlayer2)  result= "Winner is " +game.players[0].name + "!"
        else result = "It's a tie!"


        return result

    }

    /**
     *  creates a full deck of cards and shuffles it, then returns.
     */
    private fun distributeCards() = List(52) { index ->
        Card(
            CardValue.values()[index % 13],
            CardSuit.values()[index / 13],
        )
    }.shuffled()

    /**
     *  Distribute cards from the card-list in parameters into 7 lists defined size
     *  each for its own row of a pyramid and creates rows of a pyramid. Then creates the pyramid and returns it.
     *  @param cards is a list of lists of cards to be used
     */
    private fun createPyramid(cards : List<Card>) : Pyramid
    {
        val row1 = mutableListOf(cards[0])
        val row2 = mutableListOf(cards[1], cards[2])
        val row3 = mutableListOf(cards[3], cards[4], cards[5])
        val row4 = mutableListOf(cards[6], cards[7], cards[8], cards[9])
        val row5 = mutableListOf(cards[10], cards[11], cards[12], cards[13], cards[14])
        val row6 = mutableListOf(cards[15], cards[16], cards[17], cards[18], cards[19], cards[20])
        val row7 = mutableListOf(cards[21], cards[22], cards[23], cards[24], cards[25], cards[26], cards[27])
        val pyramidCards = listOf(row1, row2, row3, row4, row5, row6, row7)
        return Pyramid(pyramidCards)
    }

    /**
     *  Creates an empty drawStack and fills is with cards
     *  from the card-list in the parameter. Then returns the full drawStack.
     *  @param cards is a list of cards to be used
     */
    private fun createDrawStack(cards: List<Card>):CardStack{
        val stack = CardStack()
        stack.putOnTop(cards)
        return stack
    }


}