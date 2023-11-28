package view
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tools.aqua.bgw.core.BoardGameApplication
import service.*
import tools.aqua.bgw.animation.DelayAnimation
/**
 * Implementation of the BGW [BoardGameApplication] for the example card game "Pyramid"
 */

class PyramidApplication : BoardGameApplication("Pyramide"), Refreshable {

    /**
     * @property [rootService] is Central service from which all others are created/accessed
     * also holds the currently active game
     */
    private val rootService = RootService()

    //scenes

    /**
     * @property [startScene] holds a scene which holds a screensaver for the game
     */
    private val startScene = StartScene()

    /**
     * @property [newGameMenuScene] holds a menu scene which is shown after application start and screensaver and if the "new game" button
     *   is clicked in the gameFinishedMenuScene
     */
       private val newGameMenuScene = NewGameMenuScene(rootService).apply {
        quitButton.onMouseClicked = {
            exit()
        }

    }

    /**
     * @property [gameScene] holds the scene where the actual game takes place
     */
    private val gameScene = GameTableScene(rootService)

    /**
     * @property [gameFinishedMenuScene] is a menu scene which is shown after each finished game
     */
    private val gameFinishedMenuScene = GameFinishedMenuScene(rootService).apply {
        newGameButton.onMouseClicked = {
            showMenuScene(newGameMenuScene)
        }
        quitButton.onMouseClicked = {
            exit()
        }
    }


    init {

        // all scenes and the application itself need too
        // react to changes done in the service layer
        rootService.addRefreshables(
            this,
            gameScene,
            newGameMenuScene,
            gameFinishedMenuScene
        )
        //after the application start the first scene to be shown is a logo of the game fading in
        // the new game scene
        this.showGameScene(startScene)
        CoroutineScope(Dispatchers.IO).launch {
            delay(1500)
            runOnGUIThread{
                this@PyramidApplication.showMenuScene(newGameMenuScene,3000)
            }
        }.start()


    }

    /**
     * This method is changing the new game menu scene by game scene after player clicked "start"
     * and displays both players names
     */
    override fun refreshAfterStartGame() {
        gameScene.player1Label.text = "Player 1: "+ rootService.currentGame!!.players[0].name
        gameScene.player2Label.text = "Player 2: "+rootService.currentGame!!.players[1].name
        gameScene.player1Score.text = "0"
        gameScene.player1Score.text = "0"
        this.showGameScene(gameScene)
        this.hideMenuScene()

    }

    /**
     * This method refreshes the View after the Method endGame() in the Service layer.
     * Before game is ended, players are notified with an animation,
     * that the game ist ended. This happens in the game scene.
     * After this animation the game finished scene is shown
     */
    override fun refreshAfterEndGame() {
        gameScene.lock()
        gameScene.playAnimation(
            DelayAnimation(duration = 1000).apply {
                onFinished = {
                    this@PyramidApplication.showMenuScene(gameFinishedMenuScene)
                    gameScene.unlock()
                }
            })


        //refresh the Views
        gameScene.cardMap.clear()
        gameScene.reserveStack.clear()

        }

}