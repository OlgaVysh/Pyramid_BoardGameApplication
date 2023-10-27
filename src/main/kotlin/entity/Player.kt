class Player (val name : String, var score : Int?=null ) {
    init {
        check(name!=""){"Please enter Username"}//Username darf nicht leer sein
        check(score==null || score!! >=0){"players score can´t be less than zero"} // Keine negative Punkte erlaubt
    }

}