package view
import service.*
import entity.*
import tools.aqua.bgw.animation.DelayAnimation
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.util.BidirectionalMap
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.CompoundVisual
import java.awt.Color
/**
 * This is the main scene for the Pyramid Game. The scene shows the complete table at once.
 * The players are shown above and the current player is highlighted.
 * There is an interactive pyramid [pyramid] and two card stacks: draw stack [drawStack] is full
 * and reserve stack [reserveStack] is empty at the beginning. By clicking on the top card of the draw stack a player
 * reveals it and puts it on the reserve stack. The goal is to remove two cards ba clicking it:
 * one card from the pyramid and the top one from the reserve stack or two cards from the pyramid.
 * Only revealed cards of the pyramid are clickable and can be removed.
 * There are two buttons : [passButton] to pass a turn to another player and [endButton] to immediately terminate the game
 * without revealing the points and the winner.
 */

class GameTableScene (private val rootService: RootService) : BoardGameScene(1920, 1080), Refreshable {

    private val drawStack = LabeledStackView(posX = 1047, posY = 432, "draw stack is empty").apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->
                rootService.playerActionService.revealCard() //if the draw stack is clicked call a methode
                //in service layer to reveal a top card and put in on the reserve stack
            }
        }
    }


    val pyramid = PyramidView(480, 540)

    val reserveStack = LabeledStackView(posX = 1616, posY = 432, "reserve stack is empty").apply {
        onMouseClicked = {
            setCard(this.components.last()) //if reserve stack is clicked pass the top card to be evaluated to be removed
        }
    }

    private val passButton = Button(
        width = 407, height = 87,
        posX = 1341, posY = 944,
        text = "Passen"
    ).apply {
        visual = ColorVisual(Color(232, 18, 18))
        font = Font(30)
        onMouseClicked = {
            rootService.playerActionService.pass() }
    }

    /*val endButton = Button(
        width = 140, height = 35,
        posX = 1339, posY = 749,
        text = "Quit"
    ).apply {
        visual = ColorVisual(Color(232, 18, 18))
    }*/

    var player1Label: Label = Label(
        height = 48,
        width = 407,
        posX = 909,
        posY = 61,
        text = "Player 1: ",
    )

    var player2Label: Label = Label(
        height = 48,
        width = 407,
        posX = 1455,
        posY = 61,
        text = "Player 2: ",
    )

 //this is a notification shown in an animation
 private var note: Label = Label(
        height = 400,
        width = 800,
        posX = 570,
        posY = 360,
        text = "",
    ).apply {
        visual = ColorVisual(Color(239, 229, 229))
        isVisible = false
        isDisabled = true
        font = Font(30, Color.BLACK, "Arial", Font.FontWeight.NORMAL, Font.FontStyle.NORMAL)
    }

    val cardMap: BidirectionalMap<Card, CardView> = BidirectionalMap()

    //these are styles for the current player(highlighted) and the other one
    private val styleCurrent = Font(40, Color.BLACK, "Arial", Font.FontWeight.BOLD, Font.FontStyle.NORMAL)
    private val styleWait = Font(30, Color.BLACK, "Arial", Font.FontWeight.NORMAL, Font.FontStyle.NORMAL)

    //these variables hold the cards to be evaluated to be removed
    private var card1: CardView? = null
    private var card2: CardView? = null

    init {
        background = ColorVisual(Color(195, 244, 198))

        addComponents(
            drawStack,
            reserveStack,
            passButton,
            pyramid,
            player1Label,
            player2Label,
            //endButton,
            note
        )
        player1Label.font = styleCurrent
        player2Label.font = styleWait

    }

    /**
     * Initializes the complete GUI, i.e. the pyramid and the draw stack (the reserve stack is empty)
     */
    override fun refreshAfterStartGame() {
        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }
        val cardImageLoader = CardImageLoader()
        initializeStackView(game.drawStack, drawStack, cardImageLoader) //methods to initialize pyramid and stack
        initializePyramid(game.pyramid, pyramid, cardImageLoader)

    }

    /**
     * Initializes the [drawStack]
     */
    private fun initializeStackView(
        stack: entity.CardStack,
        stackView: LabeledStackView,
        cardImageLoader: CardImageLoader
    ) {
        stack.cards.toList().reversed().forEach { card ->
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(cardImageLoader.frontImageFor(card.cardSuit, card.cardValue)),
                back = ImageVisual(cardImageLoader.backImage)
            )
            stackView.add(cardView)
            cardMap.add(card to cardView)
        }
    }


    /**
     * Initializes the [pyramid]
     */
    private fun initializePyramid(
        pyramid: Pyramid, pyramidView: PyramidView,
        cardImageLoader: CardImageLoader
    ) {
        for (i in 0..6) {
            //every row of a pyramid is a linear layout of card views
            val cardRow = LinearLayout<CardView>(480,540,
                height = 150, width = 750,
                spacing = 40, visual = ColorVisual(255, 255, 255, 50),
                alignment = Alignment.CENTER
            )
            val row = pyramid.cards[i].toList()
            row.forEach { card ->
                val cardView = CardView(
                    height = 100,
                    width = 70,
                    front = ImageVisual(cardImageLoader.frontImageFor(card.cardSuit, card.cardValue)),
                    back = ImageVisual(cardImageLoader.backImage)
                )
                cardView.onMouseClicked = {
                    setCard(cardView)
                }

                cardRow.add(cardView)
                cardMap.add(card to cardView)
            }

            //save row(i) in the pyramid Grid Pane
            pyramidView[0,i] = cardRow

            /// reveal the cards on the edges of pyramid
            if (i == 0) {
                cardRow.components.first().flip()
            } else {
                cardRow.components.first().flip()
                cardRow.components.last().flip()
                cardRow.components.forEach { cardView -> cardView.isDisabled = true }
                cardRow.components.first().isDisabled = false
                cardRow.components.last().isDisabled = false
            }

        }
    }

    /**
     * Moves the view of the top card of the [drawStack] on top of the [reserveStack]
     */
    override fun refreshAfterRevealCard() {
        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }
        val card = game.reserveStack.cards.first()
        val cardView = cardMap.forward(card)
        moveCardView(cardView, reserveStack, true)
    }

    /**
     * moves a [cardView] from current container on top of [toStack].
     *
     * @param flip if true, the view will be flipped from [CardView.CardSide.FRONT] to
     * [CardView.CardSide.BACK] and vice versa.
     */
    private fun moveCardView(cardView: CardView, toStack: LabeledStackView, flip: Boolean = false) {
        if (flip) {
            when (cardView.currentSide) {
                CardView.CardSide.BACK -> cardView.showFront()
                CardView.CardSide.FRONT -> cardView.showBack()
            }
        }
        cardView.removeFromParent()
        toStack.add(cardView)
    }

    /**
     * Showes an animated notification that the player(name) has passed his/her turn
     */
    override fun refreshAfterPass() {
        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }
        val name: String
        val player = game.currentPlayer
        if (player == 1) name = player1Label.text.drop(10)
        else name = player2Label.text.drop(10)

        note.text = name + " has passed!"
        note.isVisible = true
        lock()
        playAnimation(
            DelayAnimation(duration = 1000).apply {
                onFinished = {
                    note.isVisible = false
                    unlock()
                }
            })
    }

    /**
     * Highlights the new current player after another players turn
     */
    override fun refreshAfterChangePlayer() {
        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }
        if (game.currentPlayer == 1) {
            player2Label.font = styleWait
            player1Label.font = styleCurrent
        } else {
            player1Label.font = styleWait
            player2Label.font = styleCurrent
        }
    }
    /**
     * Reveals new card(s) on the edges of the pyramid after card(s) was removed
     */
    override fun refreshAfterFlipCard() {

        for (i in 0..6) {
            val cardRow = pyramid[0, i]?.toList()
            if (cardRow != null) {
                if (cardRow.size>0) {
                    cardRow.first().showFront()
                    if(cardRow.size>1)cardRow.last().showFront()
                }
            }
        }


    }

    /**
     * This method is called whenether a card prom pyramid or reserve stack is clicked.
     * If a one of the cards to be removed [card1]/[card2] is not set, then the
     * @param cardView is saved as one of the cards to be evaluated (note: cards to be removed are global variables).
     * As long as both cards are set, method removePair is called.
     * If another card (third,fourth ...) is clicked by mistake, it will be disabled and will not change the
     * outcome of the turn (always the first two clicked cards are evaluated)
     */
    private fun setCard(cardView: CardView) {
        //if a player clicked on a card from pyramid/reserve stack, he/she made a choice to do the "remove pair"
        //activity and can no longer pass or reveal a card on this turn
        drawStack.isDisabled=true
        passButton.isDisabled=true

        val b1 = card1 == null //the first card to be revealed is not set yet

        val b2 = card2 == null //the second card to be revealed is not set yet

        //set the first card to current card
        if (b1) {

            //this is done to highlight the chosen card
            highlightCard(cardView)
            card1 = cardView
        }

        //set the second card to the current card
        else if (b2) {
            //this is done to highlight the chosen card
            highlightCard(cardView)
            card2 = cardView
        }
        //when both cards are already set - card not clickable
        else cardView.isDisabled = true

        //if both cards to be evaluated and removed are set - call removePair on this card views
        if (card1 != null && card2 != null) {
            removePair(card1!!, card2!!)
        }


    }

    /**
     * This method is applied on a clicked cardView and highlightes it blue
     */
    private fun highlightCard(cardView:CardView)
    {
        val card = cardMap.backward(cardView)
        val cardImageLoader = CardImageLoader()
        val cardImage = ImageVisual(cardImageLoader.frontImageFor(card.cardSuit, card.cardValue))
        val cardVisual = CompoundVisual(cardImage, ColorVisual.BLUE.apply { transparency = 0.2 })
        cardView.frontVisual = cardVisual
    }

    /**
     * This method maps a [CardView] of [card1],[card2] to a [Card] and calls removePair() in service layer
     */
    private fun removePair(card1: CardView, card2: CardView) {

        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }

        val card_1 = cardMap.backward(card1)
        val card_2 = cardMap.backward(card2)

        rootService.playerActionService.removePair(card_1, card_2)
    }

    /**
     * This method is called by removePair() in the service layer. If the card choice
     * @param isValid was valid and cards were removed,
     * it removes the [CardView] of the cards from the GUI and the map. If not - it shows an animated notification informing
     * the player that he/she must try again. In any case it refreshes the chosen cards and unlocks all the buttons, so
     * the player can choose another activity like "pass" or "reveal card" (method refreshAfterTurn())
     */
    override fun refreshAfterRemovePair(isValid: Boolean) {

        //if cards were removed, the corresponding views must be identified (pyramid/reserve stack) and removed
        if (isValid) {

            val card1 = card1
            val card2 = card2
            lock()
            playAnimation(DelayAnimation(250).apply {
                onFinished = {
                    card1?.let{first->
                        card2?.let{ second -> removeSelectedCardViews(first,second)}
                    }
                    refreshAfterTurn()
                    unlock()
                }
            })
        }

        //if card choice invalid - try again
        if (!isValid) {
            note.text = "Cards invalid. Try again!"
            note.isVisible = true
            lock()
            playAnimation(
                DelayAnimation(duration = 250).apply {
                    onFinished = {
                        note.isVisible = false
                        refreshAfterTurn()
                        unlock()
                    }
                })
        }

    }

    /**
     * This method finds the corresponding to card1 and card2 cardViews in the pyramid and/or reserve stack
     * and removes it from the View. After that it calls method refreshAfterFlipCard() to adjust the pyramid view
     * @param card1 is the first card view to be removed
     * @param card2 is the first second view to be removed
     */
    private fun removeSelectedCardViews(card1 : CardView?, card2 : CardView?)
    {
        if (!reserveStack.isEmpty() && card1 == reserveStack.components.last())
        {
            val card3 = reserveStack.pop()
            cardMap.removeBackward(card3)

            //find a cardView in the pyramid and delete it
            var isRemoved = false

            for (i in 0..6) {

                if (isRemoved) {
                    card2?.let { cardMap.removeBackward(it) }
                    break
                }

                val row = pyramid.get(0, i)
                isRemoved = card2?.let { row!!.remove(it) } == true


            }


        }
        //if card2 was from reserve stack and card1 from pyramid
        else if (!reserveStack.isEmpty() && card2 == reserveStack.components.last())
        {
            val card3 = reserveStack.pop()
            cardMap.removeBackward(card3)

            var isRemoved = false

            for (i in 0..6) {

                if (isRemoved) {
                    card1?.let { cardMap.removeBackward(it) }
                    break
                }
                val row = pyramid.get(0, i)
                isRemoved = card1?.let { row!!.remove(it) } == true

            }

        }
        else
        { //both cards were from the pyramid

            var b1 = false
            var b2 = false

            for (i in 0..6) {

                if (b1 && b2) {
                    card2?.let { cardMap.removeBackward(it) }
                    card1?.let { cardMap.removeBackward(it) }
                    break
                }
                val row = pyramid.get(0, i)
                if (!b1) {
                    b1 = card1?.let { row!!.remove(it) } == true
                }

                if (!b2) {
                    b2 = card2?.let { row!!.remove(it) } == true
                }

            }


        }
        //adjust the pyramid since the cards were removed - flip cards
        refreshAfterFlipCard()
    }



    /**
     * This method refreshes the Visual of chosen cards and unlocks all the buttons for the next turn
     * (whether for the next player or for the same player but another try)
     */
    private fun refreshAfterTurn()
    {
        for (i in 0..6) {
            val cardRow = pyramid[0, i]?.toList()
            if (cardRow != null) {
                cardRow.forEach {
                    if(it.visual is CompoundVisual){
                        it.frontVisual = (it.visual as CompoundVisual).children.first()
                    }
                    it.isDisabled = false
                }
            }

        }

        if(!reserveStack.isEmpty() ){
            reserveStack.components.last().apply{
                val card = cardMap.backward(this)
                val cardImageLoader = CardImageLoader()
                val cardImage =ImageVisual(cardImageLoader.frontImageFor(card.cardSuit, card.cardValue))
                this.frontVisual= cardImage
                isDisabled = false }
        }

        card1 = null
        card2 = null
        drawStack.isDisabled = false
        passButton.isDisabled = false
    }

}

