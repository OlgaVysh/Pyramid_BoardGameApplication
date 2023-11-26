package entity
/**
 * Entity to represent a card pyramid in the game.
 *  @property cards is a list of 7 mutable lists of cards, each of them represents a row of a pyramid :
 *  first mutable list represents the first row and contains exactly one card
 *  second mutable list represents the second row and contains exactly two cards
 */
class Pyramid (var cards : List<MutableList<Card>>) {

    /**
     * The constructor checks whether valid arguments for building a pyramid have been passed
     */
    init {

          //entity.Pyramide hat genau 7 Reihen - 7 Lists im Argument

        require(cards.size==7){"entity.Pyramid should have exact 7 rows"}

        //Erste Liste des Arguments repräsentiert die Spitze der entity.Pyramide und beinhaltet nur eine Karte

        val row1 = cards.elementAt(0)
        require(row1.size==1){"The first row of pyramid should contain 1 card"}

        //Zweite Liste beinhaltet 2 Karten für die 2te Reihe usw

        val row2 = cards.elementAt(1)
        require(row2.size==2){"The second row of pyramid should contain 2 cards"}

        val row3 = cards.elementAt(2)
        require(row3.size==3){"The third row of pyramid should contain 3 cards"}

        val row4 = cards.elementAt(3)
        require(row4.size==4){"The fourth row of pyramid should contain 4 cards"}

        val row5 = cards.elementAt(4)
        require(row5.size==5){"The fifth row of pyramid should contain 5 cards"}

        val row6 = cards.elementAt(5)
        require(row6.size==6){"The sixth row of pyramid should contain 6 cards"}

        val row7 = cards.elementAt(6)
        require(row7.size==7){"The seventh row of pyramid should contain 7 cards"}


        //flip the cards on the edge of the pyramid
        cards.forEach{

                it.first().isRevealed = true
                it.last().isRevealed = true

        }


    }

    /**
     *  output method
     */
    override fun toString(): String {
        var output = ""
        for (i in 0..cards.size - 1) {
            if(i == cards.size-1){
                output += cards.get(i).toString()
            }
            else {
                output += cards.get(i).toString() + "\n"
            }
        }
        return output
    }
}