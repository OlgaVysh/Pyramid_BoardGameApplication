package view
import service.*
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
/**
 * [MenuScene] that is used for starting a new game. It is displayed directly at program start or reached
 * when "new game" is clicked in [GameFinishedMenuScene]. After providing the names of both players,
 * [startButton] can be pressed. There is also a [quitButton] to end the program.
 */

class NewGameMenuScene (private val rootService: RootService) : MenuScene(1920, 1080), Refreshable {

    private val headlineLabel = Label(
        width = 300, height = 50, posX = 790, posY = 125,
        text = "Start New Game",
        font = Font(size = 32)
    )

    private val p1Label = Label(
        width = 100, height = 35,
        posX = 750, posY = 350,
        text = "Player 1:"
    )


     val p1Input: TextField = TextField(
        width = 200, height = 35,
        posX = 850, posY = 350,
        text = ""
     //start button is disabled until both names present
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = this.text.isBlank() || p2Input.text.isBlank()
        }
    }

     val p2Label = Label(
        width = 100, height = 35,
        posX = 750, posY = 500,
        text = "Player 2:"
    )


     val p2Input: TextField = TextField(
        width = 200, height = 35,
        posX = 850, posY = 500,
        text = ""
       //start button is disabled until both names present
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = p1Input.text.isBlank() || this.text.isBlank()
        }
    }

    val quitButton = Button(
        width = 140, height = 35,
        posX = 775, posY = 650,
        text = "Quit"
    ).apply {
        visual = ColorVisual(221, 136, 136)
    }

    val startButton = Button(
        width = 140, height = 35,
        posX = 935, posY = 650,
        text = "Start"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        isDisabled = p1Input.text.isBlank() || p2Input.text.isBlank()

    }

    init {
        addComponents(
            headlineLabel,
            p1Label, p1Input,
            p2Label, p2Input,
            startButton, quitButton
        )
        opacity=1.0
    }

}
