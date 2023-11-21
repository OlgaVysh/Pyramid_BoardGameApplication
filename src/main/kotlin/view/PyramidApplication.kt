package view
import tools.aqua.bgw.core.BoardGameApplication
import service.*
import tools.aqua.bgw.animation.DelayAnimation

class PyramidApplication : BoardGameApplication("Pyramide"), Refreshable {

    // Central service from which all others are created/accessed
    // also holds the currently active game

    private val rootService = RootService()

    //scenes

    // This menu scene is shown after application start and if the "new game" button
    // is clicked in the gameFinishedMenuScene
       private val newGameMenuScene = NewGameMenuScene(rootService).apply {
        quitButton.onMouseClicked = {
            exit()
        }

        startButton.onMouseClicked = {
            rootService.gameService.startGame(p1Input.text,p2Input.text)
            gameScene.player1Label.text += rootService.currentGame!!.players[0].name
            gameScene.player2Label.text += rootService.currentGame!!.players[1].name
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
        this.showMenuScene(newGameMenuScene)

    }

    //the new game menu scene is changed by game scene after player clicked "start"
    override fun refreshAfterStartGame() {
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

        //if players click on "new Game" button in the game finished scene, the names are not taken over
        // in the new game and will be reset.
        newGameMenuScene.p1Input.text="";
        newGameMenuScene.p2Input.text="";

        newGameMenuScene.startButton.isDisabled = newGameMenuScene.p1Input.text.isBlank() || newGameMenuScene.p2Input.text.isBlank()

        newGameMenuScene.p1Input.apply {
            onKeyTyped = {
                newGameMenuScene.startButton.isDisabled = this.text.isBlank() || newGameMenuScene.p2Input.text.isBlank()
            } }

            newGameMenuScene.p2Input.apply {
                onKeyTyped = {
                    newGameMenuScene.startButton.isDisabled =
                        this.text.isBlank() || newGameMenuScene.p1Input.text.isBlank()
                }

            }

        }

}