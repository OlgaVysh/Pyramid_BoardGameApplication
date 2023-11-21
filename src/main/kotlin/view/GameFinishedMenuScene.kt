package view

import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import service.*
import entity.*
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

    /**
     * [MenuScene] that is displayed when the game is finished. It shows the final result of the game
     * as well as the score. Also, there are two buttons: one for starting a new game and one for
     * quitting the program.
     */
    class GameFinishedMenuScene(private val rootService: RootService) : MenuScene(1920, 1080), Refreshable {



        private val headlineLabel = Label(
            width = 300, height = 50, posX = 790, posY = 125,
            text = "Game Over",
            font = Font(size = 32)
        )

        private val p2Score = Label(width = 300, height = 35, posX = 775, posY = 256)

        private val p1Score = Label(width = 300, height = 35, posX = 775, posY = 386)

        private val gameResult = Label(width = 300, height = 35, posX = 775, posY = 516).apply {
        }

        val quitButton = Button(
            width = 140, height = 35,
            posX = 775, posY = 650,
            text = "Quit"
        ).apply {
            visual = ColorVisual(221, 136, 136)
        }

        val newGameButton = Button(
            width = 140, height = 35,
            posX = 935, posY = 650,
            text = "New Game"
        ).apply {
            visual = ColorVisual(136, 221, 136)
       }

        init {
            addComponents(headlineLabel, p1Score, p2Score, gameResult, newGameButton, quitButton)
            opacity=1.0
        }

        private fun Player.scoreString(): String = "${this.name} scored ${this.score} points."
        private fun Pyramide.gameResultString(): String {
            val player1 = rootService.currentGame!!.players[0]
            val player2 = rootService.currentGame!!.players[1]
            val p1Score = player1.score
            val p2Score = player2.score
            return when {
                p1Score - p2Score > 0 -> "${player1.name} wins the game."
                p1Score - p2Score < 0 -> "${player2.name} wins the game."
                else -> "Draw. No winner."
            }
        }

        override fun refreshAfterEndGame() {

            val game = rootService.currentGame
            checkNotNull(game) { "No game running" }
            val player1 = game.players[0]
            val player2 = game.players[1]
            p1Score.text = player1.scoreString()
            p2Score.text = player2.scoreString()
            gameResult.text = game.gameResultString()


        }

    }

