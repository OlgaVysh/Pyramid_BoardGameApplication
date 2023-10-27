class Pyramid ( var cards : List<List<Card>>) {
    init { //Der Konstruktor überprüft, ob valide Argumente für den Bau einer Pyramide übergeben wurden

          //Pyramide hat genau 7 Reihen - 7 Lists im Argument

        check(cards.size==7){"Pyramid should have exact 7 rows"}

        //Erste Liste des Arguments repräsentiert die Spitze der Pyramide und beinhaltet nur eine Karte

        val row1 = cards.elementAt(0)
        check(row1.size==1){"The first row of pyramid should contain 1 card"}

        //Zweite Liste beinhaltet 2 Karten für die 2te Reihe usw

        val row2 = cards.elementAt(1)
        check(row2.size==2){"The second row of pyramid should contain 2 cards"}

        val row3 = cards.elementAt(2)
        check(row3.size==3){"The third row of pyramid should contain 3 cards"}

        val row4 = cards.elementAt(3)
        check(row4.size==4){"The fourth row of pyramid should contain 4 cards"}

        val row5 = cards.elementAt(4)
        check(row5.size==5){"The fifth row of pyramid should contain 5 cards"}

        val row6 = cards.elementAt(5)
        check(row6.size==6){"The sixth row of pyramid should contain 6 cards"}

        val row7 = cards.elementAt(6)
        check(row7.size==7){"The seventh row of pyramid should contain 7 cards"}


    }
}