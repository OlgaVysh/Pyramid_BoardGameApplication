package view
import tools.aqua.bgw.core.BoardGameApplication
import service.*
import tools.aqua.bgw.animation.DelayAnimation
/**
 * Implementation of the BGW [BoardGameApplication] for the example card game "Pyramid"
 */

class PyramidApplication : BoardGameApplication("Pyramide"), Refreshable {

    // Central service from which all others are created/accessed
    // also holds the currently active game

    private val rootService = RootService()

    //scenes

    private val startScene = StartScene(this)

    // This menu scene is shown after application start and if the "new game" button
    // is clicked in the gameFinishedMenuScene
       private val newGameMenuScene = NewGameMenuScene(rootService).apply {
        quitButton.onMouseClicked = {
            exit()
        }

    }

    //this is the te actual game takes place
    private val gameScene = GameTableScene(rootService).apply {
        endButton.onMouseClicked = {
            exit()
        }
    }

    // This menu scene is shown after each finished game
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
        //after the application start the first scene to be shown is a new game scene
        this.showGameScene(startScene)
        this.showMenuScene(newGameMenuScene,1000)
       /* startScene.lock()
        startScene.playAnimation(
            DelayAnimation(duration = 500).apply {
                onFinished = {
                    this@PyramidApplication.showMenuScene(newGameMenuScene)
                    startScene.unlock()
                }
            })*/



    }

    //the new game menu scene is changed by game scene after player clicked "start"
    override fun refreshAfterStartGame() {
        gameScene.player1Label.text = "Player 1: "+ rootService.currentGame!!.players[0].name
        gameScene.player2Label.text = "Player 2: "+rootService.currentGame!!.players[1].name
        this.showGameScene(gameScene)
        this.hideMenuScene()

    }


    override fun refreshAfterEndGame() {
        //before game is ended, players are notified with an animation,
        //that the game ist ended. This happens in the game scene.
        //After this animation the game finished scene is shown
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