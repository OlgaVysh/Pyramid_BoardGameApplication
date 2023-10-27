class CardStack ( var cards : MutableList<Card>) {
    init{
        check(cards.size<25) {"There can be no more that 24 cards in a stack"}
    }
    //Nachziehstapel besteht aus höchstens 24 Karten
}