package entity
/**
 * Entity to represent a player in the game. Besides having a [name] and the information
 * @param name is a String a player chooses for his/her name
 * [score] represents a players score and is zero at the beginning of the game, can only go up
 */

class Player (val name : String ) {

    /**
     *  name can't be empty
     */
    init {
        require(value = name!=""){"Please enter Username"}//Username darf nicht leer sein

    }

    /**
     *  @property score represents points on a player and ist always 0 for the first turn
     */
    var score : Int =0

        /**
         *  makes sure, that score only goes up while playing
         */
    set( value)
    {
        require(value>=0){"players score can´t be less than zero"}
        field=value
    }

    /**
     *  output method
     */
    override fun toString(): String = name

}