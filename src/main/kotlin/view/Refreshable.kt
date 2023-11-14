package view

interface Refreshable {
    fun refreshAfterPass() {}

    fun refreshAfterRemovePair(isValid : Boolean) {}

    fun refreshAfterRevealCard() {}

    fun refreshAfterChangePlayer() {}

    fun refreshAfterStartGame() {}

    fun refreshAfterEndGame(message : String) {}

}