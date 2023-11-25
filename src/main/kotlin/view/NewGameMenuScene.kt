package view
import service.*
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * [MenuScene] that is used for starting a new game. It is displayed directly at program start after the screensaver or reached
 * when "new game" is clicked in [GameFinishedMenuScene]. After providing the names of both players,
 * [startButton] can be pressed. There is also a [quitButton] to end the program.
 */

class NewGameMenuScene (private val rootService: RootService) : MenuScene(1920, 1080), Refreshable {

    private val headlineLabel = Label(
        width = 937, height = 145, posX = 500, posY = 179,
        text = "Start New Game",
        font = Font(size = 120)
    )

    private val p1Label = Label(
        width = 123, height = 36,
        posX = 721, posY = 472,
        text = "Player 1:"
    ).apply{font = Font(size = 30)}


     val p1Input: TextField = TextField(
        width = 479, height = 64,
        posX = 721, posY = 508,
        text = ""
     //start button is disabled until both names present
    ).apply {
        font = Font(size = 25)
        onKeyTyped = {
            startButton.isDisabled = this.text.isBlank() || p2Input.text.isBlank()
        }
    }

     val p2Label = Label(
        width = 123, height = 36,
        posX = 721, posY = 620,
        text = "Player 2:"
    ).apply{font = Font(size = 30)}


     val p2Input: TextField = TextField(
        width = 479, height = 65,
        posX = 721, posY = 656,
        text = ""
       //start button is disabled until both names present
    ).apply {
        font = Font(size = 25)
        onKeyTyped = {
            startButton.isDisabled = p1Input.text.isBlank() || this.text.isBlank()
        }
    }

    val quitButton = Button(
        width = 220, height = 105,
        posX = 721, posY = 804,
        text = "Quit"
    ).apply {
        visual = ColorVisual(231, 25, 25)
        font = Font(size = 30)
    }

    val startButton = Button(
        width = 220, height = 105,
        posX = 980, posY = 804,
        text = "Start"
    ).apply {
        visual = ColorVisual(Color(83, 227, 73))
        font = Font(size = 30)
        isDisabled = p1Input.text.isBlank() || p2Input.text.isBlank()
        onMouseClicked = {rootService.gameService.startGame(p1Input.text,p2Input.text)}
    }


    init {

        background = ColorVisual(Color(195, 244, 198))
        addComponents(
            headlineLabel,
            p1Label, p1Input,
            p2Label, p2Input,
            startButton, quitButton
        )
        opacity=1.0
    }

}
