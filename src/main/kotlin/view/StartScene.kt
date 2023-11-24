package view
import service.RootService
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.animation.DelayAnimation
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.core.MenuScene

class StartScene(private val application : PyramidApplication) :
    BoardGameScene(1920, 1080,background = ImageVisual("logo.png"))

