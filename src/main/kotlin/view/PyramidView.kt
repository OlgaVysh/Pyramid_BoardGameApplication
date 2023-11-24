package view
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.layoutviews.GridPane
import tools.aqua.bgw.core.DEFAULT_GRID_SPACING
import tools.aqua.bgw.visual.Visual

/**
 * Gridpane for the pyramid visualisation
 */
class PyramidView (posX: Number, posY: Number) :
    GridPane<LinearLayout<CardView>>(posX = posX, posY = posY, 1, 7)




