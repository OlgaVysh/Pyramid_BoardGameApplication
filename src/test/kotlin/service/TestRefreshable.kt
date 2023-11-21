package service
import entity.*
import view.*
/**
 * [Refreshable] implementation that refreshes nothing, but remembers
 * if a refresh method has been called (since last [reset])
 */

class TestRefreshable {
    var refreshAfterStartGameCalled: Boolean = false
        private set

    var refreshAfterPassCalled: Boolean = false
        private set

    var refreshAfterRemovePairCalled: Boolean = false
        private set

    var refreshAfterRevealCardCalled: Boolean = false
        private set

    var refreshAfterChangePlayerCalled: Boolean = false
        private set

    var refreshAfterEndGameCalled: Boolean = false
        private set

    var refreshAfterFlipCardCalled: Boolean = false
        private set


    /**
     * resets all *Called properties to false
     */
    fun reset() {
        refreshAfterStartGameCalled = false
        refreshAfterPassCalled = false
        refreshAfterRemovePairCalled = false
        refreshAfterRevealCardCalled = false
        refreshAfterChangePlayerCalled = false
        refreshAfterEndGameCalled = false
        refreshAfterFlipCardCalled = false
    }

    fun refreshAfterPass() {refreshAfterPassCalled = true}

    fun refreshAfterRemovePair(isValid : Boolean) {refreshAfterRemovePairCalled = true}

    fun refreshAfterRevealCard() {refreshAfterRevealCardCalled = true}

    fun refreshAfterChangePlayer() {refreshAfterChangePlayerCalled = true}

    fun refreshAfterStartGame() {refreshAfterStartGameCalled = true}

    fun refreshAfterEndGame() {refreshAfterEndGameCalled = true}

    fun refreshAfterFlipCard() {refreshAfterFlipCardCalled = true}


}