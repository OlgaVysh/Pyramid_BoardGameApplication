package view
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.core.BoardGameScene

/**
 * Screensaver for the Game "Pyramid". Is shown right after starting the game
 */
class StartScene() :
    BoardGameScene(1920, 1080,background = ImageVisual("logo.png"))

