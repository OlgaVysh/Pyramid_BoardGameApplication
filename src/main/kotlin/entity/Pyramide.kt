class Pyramide ( var opponentPassed : Boolean , var currentPlayer : Int,
                  val players : List<Player>, val cards : List<Card>,
                 val drawStack : CardStack, val reserveStack : CardStack,
                 val pyramid : Pyramid) {
    init { //Der Konstruktor überprüft, ob valide Argumente für den Bau einer Pyramide übergeben wurden

        //currentPlayer muss entweder auf 1 oder 2 gesetzt werden
        check(currentPlayer==1 || currentPlayer==2) {"invalid currentPlayer index"}

        //es gibt genau 2 Players im Spiel
        check(players.size==2){"invalid players count"}

        //es gibt genau 52 Karten im Spiel
        check(cards.size==52){"invalid cards count"}
    }
}